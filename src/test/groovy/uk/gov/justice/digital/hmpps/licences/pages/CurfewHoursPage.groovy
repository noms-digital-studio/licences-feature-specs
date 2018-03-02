package uk.gov.justice.digital.hmpps.licences.pages

import geb.Page
import org.openqa.selenium.Keys
import uk.gov.justice.digital.hmpps.licences.modules.HeaderModule

class CurfewHoursPage extends Page {

    static at = {
        browser.currentUrl.contains('/curfewHours/')
    }

    static content = {
        header { module(HeaderModule) }
    }
}