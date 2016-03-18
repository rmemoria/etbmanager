
import React from 'react';
import DatePicker from '../../components/date-picker';
import fieldControlWrapper from './field-control';
import FormUtils from '../form-utils';

/**
 * Control to support date types
 */
class DateControl extends React.Component {

	constructor(props) {
		super(props);
		this.onChange = this.onChange.bind(this);
		this.focus = this.focus.bind(this);
	}

	onChange(evt, value) {
		this.props.onChange({ schema: this.props.schema, value: value });
	}

	/**
	 * Set the component focus
	 * @return {[type]} [description]
	 */
	focus() {
		this.refs.input.focus();
	}

	render() {
		const sc = this.props.schema;

		if (sc.readOnly) {
			return FormUtils.readOnlyRender('TO BE IMPLEMENTED', sc.label);
		}

		const label = FormUtils.labelRender(sc.label, sc.required);

		return	(
			<DatePicker ref="input"
				label={label}
				onChange={this.onChange}
				help={this.props.errors}
				value={this.props.value}
				bsStyle={this.props.errors ? 'error' : null} />
			);
	}
}


DateControl.options = {
	supportedTypes: 'date'
};


DateControl.propTypes = {
	value: React.PropTypes.instanceOf(Date),
	schema: React.PropTypes.object,
	onChange: React.PropTypes.func,
	errors: React.PropTypes.any,
	resources: React.PropTypes.any
};

export default fieldControlWrapper(DateControl);
