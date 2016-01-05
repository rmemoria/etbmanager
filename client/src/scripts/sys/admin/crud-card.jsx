
import React from 'react';
import { Button, Row, Col, Alert, Badge } from 'react-bootstrap';
import { Card, WaitIcon, Fa, GridTable } from '../../components/index';


/**
 * Table view for standard CRUD operations
 */
export default class CrudCard extends React.Component {

	constructor(props) {
		super(props);
		this.state = {};
		this.newClick = this.newClick.bind(this);
		this.menuClick = this.menuClick.bind(this);
	}

	componentWillMount() {
		if (this.props.onGetValues) {
			const self = this;
			// call parent to return the values to display
			const prom = this.props.onGetValues(null);
			// must alway return a promise
			prom.then(values => self.setState({ count: values.count, values: values.list }));
		}
	}

	/**
	 * Called when new is clicked
	 * @return {[type]} [description]
	 */
	newClick() {
		this.raiseEvent({ type: 'new' });
	}

	/**
	 * Calledn when a menu item in the table is selected
	 * @param  {[type]} evt [description]
	 * @param  {[type]} key [description]
	 */
	menuClick(evt, key) {
		this.raiseEvent({ type: 'cmd', key: key.key, item: key.item });
	}

	/**
	 * Raise an event that triggers the 'onEvent' property of the table
	 * @param  {object} evt The event to be triggered
	 */
	raiseEvent(evt) {
		if (this.props.onEvent) {
			this.props.onEvent(evt);
		}
	}

	/**
	 * Create the new button
	 * @return {Component} The new button, or null if user has no permission to create a new item
	 */
	createNewButton() {
		if (this.props.readOnly) {
			return null;
		}

		return (
			<Col xs={4} sm={6} md={2}>
				<div className="pull-right">
					<Button bsStyle="default" bsSize="small" onClick={this.newClick}><Fa icon="plus"/>{'New'}</Button>
				</div>
			</Col>
			);
	}

	/**
	 * Create the search control
	 * @return {[type]} [description]
	 */
	createSearchBox() {
		if (!this.props.search) {
			return null;
		}

		return (
				<Col xs={8} sm={6} md={3}>
					<div className="input-group">
						<input type="text" placeholder="Search" className="input-sm form-control" />
						<span className="input-group-btn">
							<button type="button" className="btn btn-sm btn-default">{__('action.go')}</button>
						</span>
					</div>
				</Col>
				);
	}


	/**
	 * Create the table that will display the result
	 * @return {[type]} [description]
	 */
	createTable() {
		const values = this.state.values;

		return (
			<GridTable values={values}
				onCellRender={this.props.onCellRender}
				onCellSize={this.props.onCellSize}
				/>
			);
	}


	/**
	 * Render the header of the card
	 * @return {[type]} [description]
	 */
	headerRender() {
		const newButton = this.createNewButton();
		const searchBox = this.createSearchBox();

		// the size of the title column
		const colProps = {
			xs: 12,
			sm: 12,
			md: 12
		};
		// adjust the size of the title according to the search box and new button
		colProps.md += (searchBox ? -3 : 0) + (newButton ? -2 : 0);

		const count = this.state.count;
		const compCount = count > 0 ? <Badge className="tbl-counter">{count}</Badge> : null;

		// create the header of the card
		return (
			<Row>
				<Col {...colProps}>
					<h4>{this.props.title}{compCount}</h4>
				</Col>
				{searchBox}
				{newButton}
			</Row>
			);
	}

	/**
	 * Rende the table view
	 * @return {[type]} [description]
	 */
	render() {
		// create the header of the card
		const header = this.headerRender();

		// get the list of values
		const res = this.state.values;

		// create the content of the card
		let content;

		// if there is no value, then show the wait icon (because it is loading)
		if (!res) {
			content = <WaitIcon type="card" />;
		}
		else if (res.length === 0) {
			content = <Alert bsStyle="warning">{__('form.norecordfound')}</Alert>;
		}
		else {
			// if records were found, create the table content
			content = this.createTable();
		}


		return (
			<Card header={header}>
				{content}
			</Card>
			);
	}
}


CrudCard.propTypes = {
	title: React.PropTypes.string,
	paging: React.PropTypes.bool,
	search: React.PropTypes.bool,
	onCellRender: React.PropTypes.func,
	onCellSize: React.PropTypes.func,
	// the custom filters to be included in the panel
	filterView: React.PropTypes.func,
	// if true, the new button will not be available
	readOnly: React.PropTypes.bool,
	// event fired when user clicks on the new button
	onEvent: React.PropTypes.func,
	// data to be displayed
	onGetValues: React.PropTypes.func
};

CrudCard.defaultProps = {
	cellSize: { md: 6 }
};

