
import React from 'react';
import { Grid, Row, Col, Input, Button, Fade } from 'react-bootstrap';
import Title from '../components/title';
import { app } from '../core/app';


/**
 * Wellcome page - First page displayed under e-TB Manager first time execution
 */
export default class Welcome extends React.Component {
    constructor(props) {
        super(props);
        this.langChange = this.langChange.bind(this);
        this.contClick = this.contClick.bind(this);
    }

    /**
     * Called when user clicks on the continue button
     */
    contClick() {
        app.goto('/init/initoptions');
    }

    /**
     * Called when user select a language
     */
    langChange() {
        const elem = this.refs.langs.getInputDOMNode();
        const lang = elem.options[elem.selectedIndex].value;
        app.setLang(lang);
        window.location.reload(true);
    }

    /**
     * Render the component
     */
	render() {
		const langs = app.getState().app.languages;
		const lg = app.getLang();

		return (
            <Fade in transitionAppear>
                <Grid>
                    <Row>
                        <Col md={6} mdOffset={3}>
                            <Title text={__('init.welcome')} />
                            <p className="text-muted">{__('init.welcome.msg1')}</p>
                        </Col>
                        <Col md={6} mdOffset={3}>
                            <Input type="select" ref="langs" size={8} multiple autoFocus bsSize="large" value={[lg]} onChange={this.langChange}>
                                {langs.map((lang) =>
                                        <option key={lang.id} value={lang.id}>{lang.name}</option>
                                )
                                }
                            </Input>
                        </Col>
                        <Col md={6} mdOffset={3}>
                            <div className="pull-right">
                                <Button bsStyle="primary" pullRight bsSize="large" onClick={this.contClick}>{__('action.continue')}
                                </Button>
                            </div>
                        </Col>
                    </Row>
                </Grid>
            </Fade>
			);
	}
}
