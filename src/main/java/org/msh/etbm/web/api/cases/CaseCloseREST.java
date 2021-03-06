package org.msh.etbm.web.api.cases;

import org.msh.etbm.services.cases.caseclose.CaseCloseData;
import org.msh.etbm.services.cases.caseclose.CaseCloseService;
import org.msh.etbm.services.security.permissions.Permissions;
import org.msh.etbm.web.api.StandardResult;
import org.msh.etbm.web.api.authentication.Authenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by rmemoria on 23/5/16.
 */
@RestController
@RequestMapping("/api/cases/case")
@Authenticated(permissions = {Permissions.CASES_CLOSE})
public class CaseCloseREST {

    @Autowired
    CaseCloseService service;

    @RequestMapping(value = "/reopen/{caseId}", method = RequestMethod.GET)
    public StandardResult reopenCase(@PathVariable UUID caseId) {
        service.reopenCase(caseId);
        return new StandardResult(null, null, true);
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public StandardResult closeCase(@Valid @NotNull @RequestBody CaseCloseData req) {
        service.closeCase(req);
        return new StandardResult(null, null, true);
    }

}
