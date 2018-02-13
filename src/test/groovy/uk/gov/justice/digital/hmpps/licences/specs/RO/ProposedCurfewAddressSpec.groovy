package uk.gov.justice.digital.hmpps.licences.specs.RO

import geb.spock.GebReportingSpec
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Stepwise
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class ProposedCurfewAddressSpec extends GebReportingSpec {

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

    @PendingFeature
    def 'Shows address details' () {

    }

    @PendingFeature
    def 'Confirmation options initially unselected' () {

    }

    @PendingFeature
    def 'Further questions not shown when landlord consent is no' () {

    }

    @PendingFeature
    def 'Further questions shown when landlord consent is yes' () {

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

    @PendingFeature
    def 'Modified choices are saved after save and continue' () {

    }
}
