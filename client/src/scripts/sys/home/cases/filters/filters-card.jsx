import React from 'react';
import ReactDOM from 'react-dom';
import { Row, Col, Popover, Overlay } from 'react-bootstrap';
import { Card, AsyncButton, LinkTooltip } from '../../../../components';
import { isPromise } from '../../../../commons/utils';
import FilterPopup from './filter-popup';
import FilterBox from './filter-box';

import './filters.less';

/**
 * Display a card for filter selection
 */
export default class FilterCard extends React.Component {

	constructor(props) {
		super(props);
		this.btnRef = this.btnRef.bind(this);
		this.addFilter = this.addFilter.bind(this);
		this.hidePopup = this.hidePopup.bind(this);
		this.togglePopup = this.togglePopup.bind(this);
		this._onChange = this._onChange.bind(this);
		this._submitFilters = this._submitFilters.bind(this);
		this.removeFilter = this.removeFilter.bind(this);
	}

	componentWillMount() {
		this.state = { show: true };
	}

	/**
	 * Add a filter to the card
	 */
	addFilter(item) {
		let lst = this.state.selectedFilters;

		if (!lst) {
			lst = [];
		}

		lst.push({ filter: item, value: null });

		// toggle filter displaying
		this.setState({ show: !this.state.show, selectedFilters: lst.slice(0) });
	}

	/**
	 * Remove a filter from the list of selected filters
	 */
	removeFilter(filter) {
		const lst = this.state.selectedFilters;

		if (!lst) {
			return;
		}

		const index = lst.findIndex(item => item.filter === filter);

		lst.splice(index, 1);
		this.setState({ selectedFilters: lst.slice(0) });
	}

	/**
	 * Toggle the displaying of the filter popup
	 */
	togglePopup() {
		this.setState({ show: !this.state.show });
	}

	/**
	 * Return a reference to the button where overlay must be displayed upon
	 */
	btnRef() {
		return ReactDOM.findDOMNode(this.refs.btnadd);
	}

	/**
	 * Manually hide the popup panel
	 */
	hidePopup() {
		this.setState({ show: false });
	}

	/**
	 * Render the filters and its selected values
	 */
	renderFilters() {
		const filters = this.state.selectedFilters;
		if (!filters) {
			return null;
		}

		return filters.map(it => (
			<FilterBox key={it.filter.id}
				filter={it.filter}
				value={it.value}
				onChange={this._onChange}
				onRemove={this.removeFilter} />
		));
	}

	_onChange(filter, value) {
		const lst = this.state.selectedFilters;

		const item = lst.find(it => it.filter === filter);
		item.value = value;
		this.setState({ selectedFilters: lst.slice(0) });
	}

	/**
	 * Called when user clicks on the submit button to execute the filters
	 */
	_submitFilters() {
		const res = this.props.onSubmit(this.state.selectedFilters);
		// check if it is a promise
		if (!isPromise(res)) {
			return;
		}

		this.setState({ fetching: true });

		const self = this;
		res.then(() => self.setState({ fetching: false }))
			.catch(() => self.setState({ fetching: false }));
	}

    render() {
        return (
            <Card title={this.props.title} closeBtn onClose={this.props.onClose}>
					{
						this.renderFilters()
					}
					<Row>
						<Col sm={12} className="mtop">
							<AsyncButton className="pull-right"
								onClick={this._submitFilters}
								bsStyle="success"
								fetching={this.state.fetching}>
								{__('action.search')}
							</AsyncButton>

							<LinkTooltip ref="btnadd" toolTip={__('form.filters.add')} icon="plus"
								onClick={this.togglePopup} />
							{
								this.props.filters &&
								<Overlay
									animation
									target={this.btnRef}
									placement="right"
									show={this.state.show}
									container={this}
									rootClose
									onHide={this.hidePopup} >

									<Popover id="filter-popover" style={{ minWidth: '330px' }}>
										<FilterPopup
											filters={this.props.filters}
											onSelect={this.addFilter} />
									</Popover>
								</Overlay>
							}
						</Col>
					</Row>
            </Card>
        );
    }
}

FilterCard.propTypes = {
    title: React.PropTypes.string.isRequired,
	filters: React.PropTypes.array,
    btnLabel: React.PropTypes.node,
    onClose: React.PropTypes.func,
    onSubmit: React.PropTypes.func
};

