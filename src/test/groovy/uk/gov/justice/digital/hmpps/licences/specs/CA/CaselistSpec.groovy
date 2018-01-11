package uk.gov.justice.digital.hmpps.licences.specs.CA

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class CaselistSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()
    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.deleteLicences()
        actions.logIn('CA_USER')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Shows the caseload of HDC eligible prisoners'() {

        given: 'No licences started'
        testData.deleteLicences()

        when: 'I view the caselist'
        via CaselistPage

        then: 'I see two HDC eligible prisoners'
        hdcEligible.size() == 2
    }


    def 'Shows licence case summary details'() {

        given:
        def offenderDetails = [
                '.name'      : 'Andrews, Mark',
                '.offenderNo': 'A1235HG',
                '.location'  : 'A-C-2-002 - BERWYN (HMP)',
                '.hdced'     : '09/07/2017',
                '.crd'       : '15/12/2017',
                '.status'    : 'Not started',
        ]

        when: 'I view the case list'
        via CaselistPage

        then: 'I see the expected data for the prisoner'
        offenderDetails.each { item, value ->
            assert hdcEligible[0].find(item).text() == value
        }
    }

}