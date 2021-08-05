# Configure a Health Cloud Dev Org

## 1. Create (or get) an org with Health Cloud licenses: 

* **Option 1:** Create a Partner Dev Org Using Template with HC licenses
    * From Environment Hub (in the PBO or elsewhere), click “Create an Org” and fill in the following:
    * Purpose: Select “Test/Demo”
    * Create Using: Select “Trialforce Template ID”
    * PDE Trialforce Template ID: Enter: *0TT2E000003ZDZq* (May 13,2021), 0TT2E000003ZDND (valid as of July 29, 2020)
        * Includes work.com (http://work.com/) PSL and Health Cloud Analytics Plus licenses
        * Includes PSLs for Emergency Response for Public Sector,  Public Sector Community, Einstein Analytics for Emergency Response Management
        * ‘0TT2E000003nLtD’ (Note: this ID, for a PDE, is valid as of 1/15/20)
        * ‘0TT2E000003ZDN3’ (Valid as of June 11, 2020)
        * ‘0TT2E000003ZDN8’ (Valid as of July 10, 2020)
    * *Note:* Since this is a Developer Edition org, it will not expire. These orgs can be used for development and packaging (if not using 2GP which is recommended for new packages)
* **Option 2:** Working with other Orgs (Dev, EE)
    * Create the org however you would normally (Environment Hub, TMO, DE signup, etc)
        * Or even if you want to install and configure Health Cloud in an existing org
    * Once created, submit a Case (https://partners.salesforce.com/newPartnerCase?subtopic=LicenseRequest) in the Partner Community requesting the Health Cloud license be added

## 2. Enable access to the org from the CLI

* Type the following on the command line (note the optional fields contained in <>):
* sfdx force:auth:web:login -a <alias> --instanceurl=<login url>
* When the browser opens, enter your credentials as you normally would to login
* When the prompt comes up, select that you Allow access

## 3. Configure Salesforce to Allow HC packages to be installed

* Enable Chatter by going into Settings and: 
    * Navigate to Feature Settings → Chatter Settings
    * Select Enable and Save

* Ensure Data Protection and Privacy permission is enabled
    * From Setup, enter "Data Protection and Privacy" in the Quick Find box
    * Select Data Protection and Privacy
    * If *Make data protection details available in records* is not enabled, click Edit and enabled it
    * Then click Save

## 4. Install the required Health Cloud packages

* Install the Health Cloud package (https://industries.secure.force.com/healthcloud/) (Note: the HC Package ID referenced below is periodically updated, for the latest version, click on the link above, and copy the ID that starts with '04t')
* Install the Health Cloud extension package for Health Cloud (https://industries.secure.force.com/healthcloudextension/) 
* Install Contact Tracing package (https://industries.force.com/healthcloudcontacttracingemployees)
    * More instructions at https://help.salesforce.com/articleView?id=emergency_response_admin_emp_tracing_overview.htm&type=5
    * Requires v224.7 of Health Cloud

## 5. Deploy the metadata packages

* Ensure you are connected to the HC Dev Org
* Deploy the **force-app/main/default** directory
    * In VS Code right-click on the directory from the file navigator and click **SFDX: Deploy Source to Org**

## 6. Assign the Permissions

Run the following command, replacing [ORG_NAME] with your orgs name.
	
~~~~
sfdx force:user:permset:assign -n HC_DataLoad_Custom -u [ORG_NAME]
~~~~

## 7. Cleanup data on the target org

Run the following command, replacing [ORG_NAME] with your orgs name.

~~~~
sfdx force:apex:execute -f config/cleanup.apex -u  [ORG_NAME]
~~~~

## 8. Load the data

Install the SFDX Data Move Utility plugin

~~~~
sfdx plugins:install sfdmu
~~~~

Run the following command, replacing [ORG_NAME] with your orgs name.

~~~~
sfdx sfdmu:run --sourceusername csvfile -p data/sfdmu/ --noprompt --targetusername  [ORG_NAME]
~~~~