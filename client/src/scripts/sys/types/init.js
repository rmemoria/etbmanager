
import Form from '../../forms/form';

import AdminUnitControl from './admin-unit-control';
import YesNoControl from './yesno-control';
import UnitControl from './unit-control';
import MultiSelect from './multi-select';
import ListBoxControl from './list-box-control';
import TableFormControl from './table-form-control';
import MultiListBoxControl from './multi-list-box-control';

function register() {
	Form.registerType([
		AdminUnitControl,
		YesNoControl,
		UnitControl,
		MultiSelect,
		ListBoxControl,
		TableFormControl,
		MultiListBoxControl
		]);
}

export { register };
