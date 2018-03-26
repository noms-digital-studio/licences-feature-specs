package uk.gov.justice.digital.hmpps.licences.specs.common

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.CaselistPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class CaselistTabSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    @Shared
    def licences = [
            'A0001XX': 'eligibility/unstarted',
            'A0002XX': 'assessment/unstarted',
            'A0003XX': 'finalchecks/unstarted',
            'A0004XX': 'finalchecks/postponed',
            'A0005XX': 'decision/unstarted',
            'A0006XX': 'decision/approved',
            'A0007XX': 'decision/refused',

            'A0008XX': 'eligibility/started',
            'A0009XX': 'assessment/reporting',
            'A0010XX': 'decision/address-rejected',
    ]

    def setupSpec() {
        testData.deleteLicences()
        licences.each { nomisId, file ->
            testData.loadLicence(file, nomisId)
        }
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'put some licences in my database'() {

        when: 'I want some sample licences in my local DB'

        then: 'I can just run this test'

        and: 'Now I can log in as XX_USER_MULTI and see lots of records'
    }

    @Unroll
    def 'shows some licences on the #tab tab for CA'() {

        given: 'logged in'
        actions.logIn('CA_MULTI')

        when: 'I view the caselist'
        to CaselistPage, tab

        then: 'I see the right number of prisoners in each tab'
        hdcEligible.size() == count

        where:
        tab           | count
        'ready'       | 14
        'submittedRo' | 2
        'finalChecks' | 2
        'submittedDm' | 2
        'approved'    | 1
        'refused'     | 1
    }

    @Unroll
    def 'shows some licences on the #tab tab for RO'() {

        given: 'logged in'
        actions.logOut()
        actions.logIn('RO_MULTI')

        when: 'I view the caselist'
        to CaselistPage, tab

        then: 'I see the right number of prisoners in each tab'
        hdcEligible.size() == count

        where:
        tab            | count
        'ready'        | 2
        'submittedPca' | 3
        'postponed'    | 1
        'active'       | 2
    }

    @Unroll
    def 'shows some licences on the #tab tab for DM'() {

        given: 'logged in'
        actions.logOut()
        actions.logIn('DM_MULTI')

        when: 'I view the caselist'
        to CaselistPage, tab

        then: 'I see the right number of prisoners in each tab'
        hdcEligible.size() == count

        where:
        tab        | count
        'ready'    | 2
        'approved' | 1
        'refused'  | 1
    }
}
