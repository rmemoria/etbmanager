import React from 'react';
import { Row, Col, ButtonToolbar, Button } from 'react-bootstrap';
import Form from '../forms/form';
import Fa from '../components/fa';


export default class TableForm extends React.Component {

	constructor(props) {
		super(props);

		if (__DEV__) {
			if (!props.fschema) {
				throw new Error('fschema (form schema) must be defined in FormTable');
			}

			if (props.ctitles) {
				props.ctitles.map(c => {
					if (!c.size) {
						throw new Error('No column size specified in ReactTable');
					}
				});
			}
		}

		this.onFormChange = this.onFormChange.bind(this);
		this.validate = this.validate.bind(this);
		this.state = { errorsarr: [] };
	}

	/**
	 * Return the React component that will be displayed on the screen
	 * @return {[type]} [description]
	 */
	titleRender() {
		if (!this.props.ctitles) {
			return null;
		}

		const cols = this.props.ctitles;

		return (
			<Row className="tbl-title">
			{
				cols.map((col, index) => {
					const colProps = Object.assign({}, col.size);
					return (
						<Col key={index} {...colProps} className={this.alignClass(col)}>
							{col.title}
						</Col>
						);
				})
			}
			</Row>
			);
	}

	alignClass(col) {
		switch (col.align) {
			case 'right':
				return 'col-right';
			case 'center':
				return 'col-center';
			default:
				return null;
		}
	}

	contentRender() {
		var rowsQuantity = this.props.rowsQuantity;
		var i;

		// Clean docs array to have the same quantity as rows
		if (rowsQuantity < this.props.docs.length && this.props.docs.length > 1) {
			i = rowsQuantity + 1;
			while (rowsQuantity !== this.props.docs.length) {
				this.props.docs.pop();
				this.state.errorsarr.pop();
			}
		}


		if (!rowsQuantity || rowsQuantity < 1) {
			rowsQuantity = 1;
		}

		var rows = [];
		for (i = 0; i < rowsQuantity; i++) {
			rows[i] = this.getNewRow(i);
		}

		return (<div>{rows.map(row => row)}</div>);
	}

	getNewRow(key) {
		if (key < 0) {
			return null;
		}

		// add a new doc if don't exists to have the same docs quantity as rows
		if (!this.props.docs[key]) {
			this.props.docs.push({});
		}

		// add a new erros if don't exists to have the same errors quantity as rows
		if (!this.state.errorsarr[key]) {
			this.state.errorsarr.push({});
		}

		return 	(<Row>
					<Col sm={12}>
						<Form ref={'form' + key}
							schema={this.props.fschema}
							key={key}
							doc={this.props.docs[key]}
							onChange={this.onFormChange}
							errors={this.state.errorsarr[key]} />
					</Col>
				</Row>
				);
	}

	validate() {
		var i;
		var errors = null;
		for (i = 0; i < this.props.rowsQuantity; i++) {
			const e = this.state.errorsarr;
			e[i] = this.refs['form' + i].validate();
			if (e[i]) {
				errors = e[i];
			}
		}

		this.forceUpdate();
		return errors;
	}

	onFormChange() {
		if (this.props.onChange) {
			this.props.onChange();
		}
	}

	render() {
	var buttons =	(<ButtonToolbar className={'def-margin-bottom'}>
						<Button onClick={this.props.addRow}><Fa icon={'plus'}/></Button>
						<Button onClick={this.props.remRow}><Fa icon={'minus'}/></Button>
					</ButtonToolbar>);

		return (
			<div>
				{
					this.titleRender()
				}
				{
					this.contentRender()
				}
				{
					buttons
				}
			</div>
			);
	}
}

TableForm.propTypes = {
	ctitles: React.PropTypes.array,
	rowsQuantity: React.PropTypes.number,
	className: React.PropTypes.string,
	docs: React.PropTypes.array,
	addRow: React.PropTypes.func,
	remRow: React.PropTypes.func,
	fschema: React.PropTypes.object,
	onChange: React.PropTypes.func
};
