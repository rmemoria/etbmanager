import React from 'react';
import { app } from '../core/app';

export default class SessionUtils {

	/**
	 * Redirect to the home page of the application
	 * @return {[type]} [description]
	 */
	static gotoHome() {
		window.location.hash = SessionUtils.homeHash();
	}

	/**
	 * Return the URL hat must be used in the home link of the toolbar
	 * @return {[type]} [description]
	 */
	static homeHash() {
		const session = app.getState().session;
		switch (session.view) {
			case 'COUNTRY': return SessionUtils.workspaceHash();
			case 'ADMINUNIT': return '#/sys/home/adminunit';
			// default is the unit page
			default: return SessionUtils.unitHash();
		}
	}

	static workspaceHash(defaultView) {
		return '#/sys/home/workspace' + (defaultView ? defaultView : '');
	}

	static unitHash(unitId, defaultView) {
		const url = '#/sys/home/unit' + (defaultView ? defaultView : '') + '?id=';
		return url + (unitId ? unitId : app.getState().session.unitId);
	}

	static adminUnitHash(auId, defaultView) {
		return '#/sys/home/adminunit' + (defaultView ? defaultView : '') + '?id=' + auId;
	}

	static caseHash(caseId) {
		return '#/sys/home/cases/details?id=' + caseId;
	}

	/**
	 * Generate a node component to display the full name of an administrative unit
	 * followed by its links. If addWorkspace is true, a second line is included with the workspace name
	 * @param  {[type]} adminUnit    The object containing data about an administrative unit
	 * @param  {[type]} addWorkspace If true, include the workspace under the name of the workspace
	 * @return {[type]}              [description]
	 */
	static adminUnitLink(adminUnit, addWorkspace, addMain, defaultView) {
		const lst = [];

		// admin unit was informed ?
		if (adminUnit) {
			// add links of parents
			for (var k = 0; k < 4; k++) {
				const p = adminUnit['p' + k];
				if (p) {
					const hash = SessionUtils.adminUnitHash(p.id);
					lst.push(<a key={k} href={hash}>{p.name}</a>);
					lst.push(<span key={'s' + k}>{', '}</span>);
				}
			}
			// add link of main item
			if (addMain) {
				lst.push(<a key="sel" href={SessionUtils.adminUnitHash(adminUnit.id, defaultView)}>{adminUnit.name}</a>);
			} else {
				lst.pop();
			}
		}

		return (
			<span>
				{lst}
				{
					addWorkspace &&
					<div>
						<a href={SessionUtils.workspaceHash()}>{app.getState().session.workspaceName}</a>
					</div>
				}
			</span>
			);
	}

	/**
	 * Generate a node component to display the full name of an administrative unit
	 * If addWorkspace is true, a second line is included with the workspace name
	 * @param  {[type]} adminUnit    [description]
	 * @param  {[type]} addWorkspace [description]
	 * @return {[type]}              [description]
	 */
	static adminUnitDisplay(adminUnit, addWorkspace, addMain) {
		let unitName;

		// admin unit was informed ?
		if (adminUnit) {
			const lst = [];
			// add links of parents
			for (var k = 0; k < 4; k++) {
				const p = adminUnit['p' + k];
				if (p && p.name) {
					lst.push(p.name);
				}
			}
			// add link of main item
			if (addMain) {
				lst.push(adminUnit.name);
			}
			unitName = lst.join(', ');
		}

		return (
			<div>
				{unitName}
				{
					addWorkspace &&
					<div>
						{app.getState().session.workspaceName}
					</div>
				}
			</div>
			);
	}

	/**
	 * Generate the display name of the person using the given person name object
	 */
	static nameDisplay(name) {
		const nameComp = app.getState().session.patientNameComposition;

		const joins = (names, separator) => names.join(separator);

		switch (nameComp) {
			case 'FIRSTSURNAME': return joins([name.name, name.middleName], ' ');
			case 'SURNAME_FIRSTNAME': return joins([name.middleName, name.name], ', ');
			case 'FIRST_MIDDLE_LASTNAME': return joins([name.name, name.middleName, name.lastName], ' ');
			case 'LAST_FIRST_MIDDLENAME': return joins([name.lastName, name.name, name.middleName], ', ');
			case 'LAST_FIRST_MIDDLENAME_WITHOUT_COMMAS': return joins([name.lastName, name.name, name.middleName], ' ');
			default: return name.name;
		}
	}

	static classifDisplay(cla, diag) {
		const lists = app.getState().app.lists;
		const list = lists['CaseClassification' + cla];
		return list[diag];
	}

	static caseStateDisplay(state) {
		const list = app.getState().app.lists.CaseState;
		return list[state];
	}
}
