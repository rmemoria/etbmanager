
import React from 'react';
import FormUtils from '../../forms/form-utils';
import { Fa, SelectionBox } from '../../components/index';


/**
 * Control for yes-no selection
 */
export default class YesNoControl extends React.Component {

	static typeName() {
		return 'yesNo';
	}

	static displayText(value) {
		return value ? __('global.yes') : __('global.no');
	}

	constructor(props) {
		super(props);
		this.onChange = this.onChange.bind(this);
	}

	readOnlyRender(schema) {
		const labelelem = schema.label ? <label className="control-label">{FormUtils.labelRender(schema.label)}</label> : null;
		return (
			<div className="form-group">
				{labelelem}
				<div className="form-control-static autoscroll">
					{this.props.value === true ? <Fa icon="check" className="text-primary" /> : <Fa icon="times-circle" className="text-danger" />}
				</div>
			</div>
			);
	}

	onChange() {
		if (this.props.onChange) {
			const val = this.refs.input.getValue();
			this.props.onChange({ schema: this.props.schema, value: val });
		}
	}


	optionRender(val) {
		return val ?
			<div><Fa icon="check-circle" className="text-success"/>{__('global.yes')}</div> :
			<div><Fa icon="times-circle" className="text-danger"/>{__('global.no')}</div>;
	}


	editRender(schema) {
		const err = this.props.errors;
		const options = [true, false];

		return (
			<SelectionBox label={FormUtils.labelRender(schema.label, schema.required)}
				help={err} ref="input"
				onChange={this.onChange}
				options={options}
				optionDisplay={this.optionRender}
				wrapperClassName="size-2"
				bsStyle={err ? 'error' : null} value={this.props.value} />
			);
	}

	render() {
		// if there is no form, just display an icon
		if (this.props.noForm) {
			return this.props.value ? <Fa icon="check" className="text-primary" /> : <Fa icon="times-circle" className="text-danger" />;
		}

		const schema = this.props.schema || {};

		return schema.readOnly ? this.readOnlyRender(schema) : this.editRender(schema);
	}
}

YesNoControl.propTypes = {
	value: React.PropTypes.bool,
	onChange: React.PropTypes.func,
	errors: React.PropTypes.any,
	schema: React.PropTypes.object,
	noForm: React.PropTypes.bool
};
