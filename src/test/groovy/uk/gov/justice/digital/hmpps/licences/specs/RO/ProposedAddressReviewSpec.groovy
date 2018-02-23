package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.pages.ProposedAddressReviewPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ProposedAddressReviewSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.deleteLicences()
        actions.logIn('RO')
    }

    def cleanupSpec() {
        actions.logOut()
    }

    def 'Shows address details' () {

        given: 'A licence record with a proposed curfew address'
        testData.loadLicence('processing-ro/unstarted')

        when: 'I go to the address review page'
        actions.toAddressReviewPageFor('A0001XX')
        at ProposedAddressReviewPage

        then: 'I see the address details'
        street.text() == 'Street'
        town.text() == 'Town'
        postCode.text() == 'AB1 1AB'

        // todo these all need formatting/capitalising
        // todo check other values
    }

    def 'Confirmation options initially unselected' () {

        when: 'At address review page'
        at ProposedAddressReviewPage

        then:
        landlordConsentRadios.checked == null
        manageSafelyRadios.checked == null
    }

    @PendingFeature
    def 'Further questions not shown when landlord consent is no' () {

    }

    @PendingFeature
    def 'Further questions shown when landlord consent is yes' () {

    }

    @PendingFeature
    def 'Electricity supply answer previously given by CA' () { // is this really what we are meant to do? seems odd.

    }

    @PendingFeature
    def 'Further details not shown when managed safely is yes' () {

    }

    @PendingFeature
    def 'Further details shown when landlord consent is no' () {

    }

    @PendingFeature
    def 'Modified choices are not saved on return to tasklist' () {

    }

    def 'Modified choices are saved after save and continue' () {

        given: 'At address review page'
        actions.toAddressReviewPageFor('A0001XX')

        when: 'I enter new values'
        landlordConsentRadios.checked = 'Yes'

        and: 'I save and continue'
        find('#continueBtn').click()

        and: 'I return to the address review page'
        actions.toAddressReviewPageFor('A0001XX')

        then: 'I see the previously entered values'
        landlordConsentRadios.checked == 'Yes'
    }
}
