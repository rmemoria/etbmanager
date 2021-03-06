
import React from 'react';
import { RouteView } from '../../components/router';

/** Pages of the public module */
import Workspace from './workspace';
import Index from './index';
import Unit from './unit';
import AdminUnit from './admin-unit';

import NewNotif from './cases/newnotif';
import Details from './cases/details';


/**
 * Initial page that declare all routes of the module
 */
export default class HomeRoutes extends React.Component {

	render() {
		const routes = RouteView.createRoutes([
			{
				path: '/index',
				view: Index,
				title: __('home'),
				default: true
			},
			{
				path: '/unit',
				view: Unit,
				title: 'Unit'
			},
			{
				path: '/cases/newnotif',
				view: NewNotif,
				title: __('cases.newnotif')
			},
			{
				path: '/cases/details',
				view: Details,
				title: __('cases.details')
			},
			{
				path: '/workspace',
				view: Workspace,
				title: __('Workspace')
			},
			{
				path: '/adminunit',
				view: AdminUnit,
				title: __('AdministrativeUnit')
			}
		]);

		return (
			<RouteView routes={routes} />
			);
	}
}
