package uk.gov.justice.digital.hmpps.licences.specs.assessment

import geb.spock.GebReportingSpec
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll
import uk.gov.justice.digital.hmpps.Stage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.LicenceConditionsAdditionalPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.CurfewHoursPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.LicenceDetailsPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.CurfewAddressReviewPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.ReportingInstructionsPage
import uk.gov.justice.digital.hmpps.licences.pages.assessment.RiskManagementPage
import uk.gov.justice.digital.hmpps.licences.util.Actions
import uk.gov.justice.digital.hmpps.licences.util.TestData

@Stepwise
class LicenceDetailsSpec extends GebReportingSpec {

    @Shared
    TestData testData = new TestData()

    @Shared
    Actions actions = new Actions()

    def setupSpec() {
        testData.loadLicence('processing-ro/reporting')
        actions.logIn('RO')
        to LicenceDetailsPage, 'A0001XX'
    }

    def cleanupSpec() {
        actions.logOut()
    }

    @Stage
    def 'Shows offender details'() {

        when: 'I view the licence details summary page for the licence record'
        at LicenceDetailsPage

        then: 'I see the expected offender details data'
        offender.details.name == 'Andrews, Mark'
        offender.details.nomisId == 'A0001XX'
        offender.details.dob == '22/10/1989'
        offender.details.roName == 'Jessy Jones'
        offender.details.externalLocation == 'Licence Auto Test Prison'
        offender.details.offences == "Cause exceed max permitted wt of artic' vehicle - No of axles/configuration (No MOT/Manufacturer's Plate)"
        offender.details.crd == '15/10/2019'
        offender.details.hdced == '13/07/2019'
        offender.details.photoDate == 'Uploaded: 05/07/2017'

//        Pending stage data
//        offender.details.internalLocation == 'A-1-1'
//        offender.details.sed == '01/08/2019'
//        offender.details.led == '02/08/2019'
//        offender.details.pssed == '03/08/2019'
    }

    def 'Shows address details'() {

        when: 'I view the licence details summary page for the licence record'
        at LicenceDetailsPage

        then: 'I see the address details'
        curfew.address.line1 == 'Street'
        curfew.address.town == 'Town'
        curfew.address.postCode == 'AB1 1AB'
        curfew.address.telephone == '0123 456789'

        and: 'I see the occupier details'
        curfew.occupier.name == 'Main Occupier'
        curfew.occupier.age == '21'
        curfew.occupier.relation == 'Brother'

        and: 'I see the other residents details'
        curfew.residents.size() == 2

        curfew.residents[0].name == 'Other Resident'
        curfew.residents[0].age == '10'
        curfew.residents[0].relation == 'Son'

        curfew.residents[1].name == 'Yet Another'
        curfew.residents[1].age == '20'
        curfew.residents[1].relation == 'Wife'

        and: 'I see the review details'
        curfew.reviewAnswers.cautioned == 'No'
        curfew.reviewAnswers.consent == 'Yes'
        curfew.reviewAnswers.homeVisit == 'Yes'
        curfew.reviewAnswers.electricity == 'Yes'
        curfew.reviewAnswers.safety == 'Yes'

    }

    def 'Shows curfew hours details'() {

        when: 'I view the licence details summary page for the licence record'
        at LicenceDetailsPage

        then: 'I see the curfew hours details'
        curfew.curfewHours('First Night') == '18:30 - 10:11'
        curfew.curfewHours('Monday') == '21:22 - 08:09'
        curfew.curfewHours('Tuesday') == '19:00 - 07:00'
        curfew.curfewHours('Wednesday') == '19:00 - 07:00'
        curfew.curfewHours('Thursday') == '19:00 - 07:00'
        curfew.curfewHours('Friday') == '19:00 - 07:00'
        curfew.curfewHours('Saturday') == '19:00 - 07:00'
        curfew.curfewHours('Sunday') == '18:19 - 06:07'
    }

    def 'Shows conditions details'() {

        when: 'I view the licence details summary page for the licence record'
        at LicenceDetailsPage

        then: 'I see the licence conditions details'
        conditions.additionalConditions.size() == 2

        conditions.additionalConditionsTitle[0] == 'Condition 1'
        conditions.additionalConditionsName[0] == 'Technology -- Cameras and photos'

        conditions.additionalConditionsTitle[1] == 'Condition 2'
        conditions.additionalConditionsContent[1] == 'First bespoke condition'
    }

    def 'Shows risk management details'() {

        when: 'I view the licence details summary page for the licence record'
        at LicenceDetailsPage

        then: 'I see the risk management details'
        risk.answers.planningActions == 'No'
        risk.answers.information == 'No'
        risk.answers.victimLiaison == 'No'
    }

    def 'Shows reporting details'() {

        when: 'I view the licence details summary page for the licence record'
        at LicenceDetailsPage

        then: 'I see the reporting details'

        reporting.name == 'Reporting Name'

        reporting.address.line1 == 'Street'
        reporting.address.town == 'Town'
        reporting.address.postCode == 'AB1 1AB'

        reporting.address.telephone == '0123 456789'
    }

    @Unroll
    def 'Shows link to change #section details'() {

        given: 'Viewing licence details summary'
        to LicenceDetailsPage, 'A0001XX'

        when: 'I click the change details link for a section'
        changeDetailsLink(section).click()

        then: 'I see the corresponding section page'
        at page

        where:
        section       | page
        'address'     | CurfewAddressReviewPage
        'curfewHours' | CurfewHoursPage
        'conditions'  | LicenceConditionsAdditionalPage
        'risk'        | RiskManagementPage
        'reporting'   | ReportingInstructionsPage
    }
}