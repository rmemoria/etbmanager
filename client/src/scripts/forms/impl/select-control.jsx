
import React from 'react';
import fieldControlWrapper from './field-control';
import FormUtils from '../form-utils';
import { SelectionBox } from '../../components/index';
import { isPromise } from '../../commons/utils';

/**
 * Used in the Form library. Provide input data of string and number types
 */
class SelectControl extends React.Component {

	constructor(props) {
		super(props);
		this.onChange = this.onChange.bind(this);
		this.focus = this.focus.bind(this);
	}

	componentWillMount() {
		if (!this.props.resources) {
			const options = FormUtils.createOptions(this.props.schema.options);
			if (options && !isPromise(options)) {
				this.setState({ options: options });
			}
		}
	}

	/**
	 * Return request to be sent to server, if necessary
	 * @param  {[type]} schema [description]
	 * @return {[type]}        [description]
	 */
	static getServerRequest(schema, val, doc) {
		return FormUtils.optionsRequest(schema, doc);
	}


	/**
	 * Set the component focus
	 * @return {[type]} [description]
	 */
	focus() {
		this.refs.sel.getInputDOMNode().focus();
	}

	/**
	 * Called when user changes the value in the control
	 * @return {[type]} [description]
	 */
	onChange() {
		const sc = this.props.schema;
		const value = this.refs.sel.getValue();

		this.props.onChange({ schema: sc, value: value ? value.id : null });
	}


	render() {
		const sc = this.props.schema;
		const errors = this.props.errors;

		const wrapperClazz = sc.controlSize ? 'size-' + sc.controlSize : null;

		const options = this.props.resources || this.state.options;

		let value = this.props.value;

		// get the value according to the option
		value = options.find(item => item.id === value);

		// rend the selection box
		return (
			<SelectionBox ref="sel"
				options={options}
				optionDisplay="name"
				label={FormUtils.labelRender(sc.label, sc.required)}
				onChange={this.onChange}
				value={value}
				help={errors}
				noSelectionLabel={sc.required ? null : '-'}
				wrapperClass={wrapperClazz}
				bsStyle={errors ? 'error' : null} />
		);
	}

}

SelectControl.options = {
	supportedTypes: 'select'
};


SelectControl.propTypes = {
	value: React.PropTypes.any,
	schema: React.PropTypes.object,
	onChange: React.PropTypes.func,
	errors: React.PropTypes.any,
	resources: React.PropTypes.any
};

export default fieldControlWrapper(SelectControl);
