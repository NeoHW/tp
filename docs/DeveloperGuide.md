---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# PatientSync Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## 1. Introduction

### **1.1 Product Overview**

PatientSync addresses a crucial gap in the current hospital systems by providing nurses with a comprehensive tool to manage patient information beyond administrative details.

In many hospitals, the existing systems typically offer basic administrative information such as patient names and contact details. However, they often lack the capacity to delve into the intimate details of patient care.

This app can help with personalised and effective care by:
* viewing and managing upcoming checkup and appointment dates for each patient.
* Utilising tags to categorize patients into groups based on conditions, treatment plans, or other criteria.

--------------------------------------------------------------------------------------------------------------------

### 1.2 Setting up, getting started

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

### 1.3 Acknowledgements

PatientSync is a brownfield Java Project based on the AB3 project template created by the SE-EDU initiative.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## 2 Design

### 2.1 Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**2.1.1. Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2324S2-CS2103-F09-2/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2324S2-CS2103-F09-2/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#2-2-ui-component): The UI of the App.
* [**`Logic`**](#2-3-logic-component): The command executor.
* [**`Model`**](#2-4-model-component): Holds the data of the App in memory.
* [**`Storage`**](#2-5-storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#2-6-common-classes) represents a collection of classes used by multiple other components.

<div style="page-break-after: always;"></div>

**2.1.2. How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>

### 2.2 UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2324S2-CS2103-F09-2/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component" />

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PatientListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2324S2-CS2103-F09-2/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2324S2-CS2103-F09-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Patient` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### 2.3 Logic component

**API** : [`Logic.java`](https://github.com/AY2324S2-CS2103-F09-2/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` and `DeleteCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a patient).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<div style="page-break-after: always;"></div>

### 2.4 Model component
**API** : [`Model.java`](https://github.com/AY2324S2-CS2103-F09-2/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="800" />


The `Model` component,

* stores the PatientSync data i.e., all `Patient` objects (which are contained in a `UniquePatientList` object).
* stores the currently 'selected' `Patient` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Patient>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Patient` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Patient` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="800" />

</box>

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 2.5 Storage component

**API** : [`Storage.java`](https://github.com/AY2324S2-CS2103-F09-2/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="800" />

The `Storage` component,
* can save both PatientSync data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

--------------------------------------------------------------------------------------------------------------------

### 2.6 Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## 3 Implementation

This section describes some noteworthy details on how certain features are implemented.

### 3.1 Adding a Patient

#### Introduction

The `AddCommand` class is responsible for adding new patient's information in PatientSync.

#### Specifications

* AddCommand, as defined by the `AddCommand` class, contain parameters which consists of: 
  *  `patientHospitalId`: String of non-negative numeric characters which uniquely identifies the patient, 
  * `name`, `preferredName`: String which contains alphanumeric characters and spaces,
  *  `foodPreference`, `familyCondition`, `hobby`: String and all kinds of characters,
  *  `tag`: String which are alphanumeric.
* `tag` field is optional in the AddCommand and can be added later on using the `AddTagsCommand`.
* A patient can have more than one `f/FOOD_PREFERENCE`, `c/FAMILY_CONDITION` and `h/HOBBY`.
* If any of the fields such as `patientHospitalId`, `name`, `preferredName` are repeated during the adding of patient, an error message will be thrown.
* If there are any missing fields, an error message will be thrown.

The activity diagram below outlines the steps involved when a user initiates a Add command.
<puml src="diagrams/AddActivityDiagram.puml" alt="AddActivityDiagram" />

<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Given below is an example usage scenario and how the group creation mechanism behaves at each step.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes the `add id/ 12347 n/ Mary Jane p/ Mary f/ Korean c/ Lives with only daughter h/ Watch Drama`
command to add a new Patient whose patient hospital ID is `12347`,
with the name `Mary Jane` and preferred name `Mary`, likes to eat `Korean` food and current family condition is 
`Lives with only daughter` and likes to `Watch drama`.

Step 3: The `AddCommandParser` will be called to validate the input, ensuring that the fields are valid with correct 
data types and no duplicates of fields.
* Upon successful validation, it creates a `Patient` instance.

Step 4: The newly added Patient will be added to the end of list, shown in the UI. 

The following UML sequence diagram illustrates how the `AddCommand` operation works.
<puml src="diagrams/AddSequenceDiagram.puml" alt="Add Sequence Diagram" />

<box type="info" seamless>

**Note:** The lifeline for `AddCommandParser` and `AddCommand` should end at the destroy marker (X) but
due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

#### Design Considerations

**Aspect: Handle Duplicated Fields**
* **Alternative 1 (current choice)**: Returns error message, prompt user to enter the correct format.
    * Pros: Ensure the consistency of entries of the input command.
    * Cons: User has to retype the `AddCommand` once again instead of the specific field.
<br></br>
*  **Alternative 2**: Add patient's information to the list, for duplicated fields, take the first one.
    * Pros: User does not have to retype the command.
    * Cons: Introduce ambiguity, the first repeated field may not be what user wish to enter.

**Aspect: Choice of PatientHospitalId field**
* **Alternative 1 (current choice)**: Use patient's ID in the hospital.
  * Pros: Uniquely identifies the patient in the hospital.
  * Cons: First time user will be confused about the usage of `PatientHospitalId`.
    <br></br>
* **Alternative 2**: Use patient's NRIC as the ID.  
  * Pros: Easier to type and refer.
  * Cons: NRIC is confidential and sensitive information, it breaches PDPA since this is a personal use application.

**Aspect: Handle Existing Patient**
* **Alternative 1 (current choice)**: Returns error message upon user adds a new patient with existing `patientHospitalId`
    * Pros: Ensures that no same patient will be added to PatientSync.
    * Cons: User has to enter patient's hospital ID in care to ensure no duplications.
<br></br>
* **Alternative 2**: Check duplicated patient by patient's `name`.
    * Pros: Easier to view as patient's `name` will be easier to be remembered.
    * Cons: Patients may have the same name.

**Aspect: Handle Multiple Inputs for the Same Field**
* **Alternative 1 (current choice)**: Allow duplicate prefixes for certain fields
  * Pros: Easier to view as listings will be shown without commas, provide clearer view to user.
  * Cons: User may find it confusing if same prefix not being entered repeatedly.
* **Alternative 2**: Adds `,` to the specific field
  * Pros: Ensures that there are no duplicate prefixes in the input command.
  * Cons: Display may be messy if inputs are long with more than 1 comma. 
    <br></br>

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.2 Adding Tags to a Patient

#### Introduction

The `AddTagsCommand` class is responsible for adding one or more tags to a patient in PatientSync.

#### Specifications

* Tags, as defined by the `Tag` class, are alphanumeric characters with or without spaces, and repeated tags in the command are added as a single tag.
* The addition of tags is cumulative, and new tags will be added to the existing set of tags for the patient, preserving the previously assigned tags.
*  If the patient has an existing tag that is provided in the command, it will not be added, and the output would be logged and shown to the user.

The activity diagram below outlines the steps involved when a user initiates an Add Tags command.
<puml src="diagrams/AddTagsActivityDiagram.puml" alt="AddTagsActivityDiagram" />

<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Given below is an example usage scenario and how the tag addition process behaves at each step:

Step 1: The user accesses the PatientSync application.

Step 2: The user executes the `addt 1 t/christian t/fall risk` command to add the tags `christian` and `fall risk` to patient 1 in the displayed patient list. The `AddTagsCommandParser` will be called to validate the input, ensuring that the index is valid and at least one tag is provided. Upon successful validation, it creates an `AddTagsCommand` instance.

<box type="info" seamless>
<b>Note</b>: Since multiple inputs are allowed, a set of tags are passed around, each of which is to be added if the above requirements are met.
</box>

The following sequence diagram shows how the Add Tags operation works:
<puml src="diagrams/AddTagsSequenceDiagram.puml" alt="AddTagsSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `AddTagsCommandParser` and `AddTagsCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

#### Design Considerations

**Aspect: Skip duplicate Tags in command**

* **Alternative 1 (current choice)**: Repeated tags in commands are added as a single tag.
    * Pros: Simplifies tag management, avoids redundancy.
    * Cons: Requires additional logic to detect and merge repeated tags.
<br></br>
* **Alternative 2**: Each tag is added individually, including duplicates.
    * Pros: Explicitly shows every tag provided.
    * Cons: May clutter patient data with redundant tags.

**Aspect: Cumulative Tag Addition**

* **Alternative 1 (current choice)**: Cumulative addition of tags to existing set.
    * Pros: Preserves previous tags, allows for gradual building of patient profile.
    * Cons: Requires additional memory for storing updated tag sets.
<br></br>
* **Alternative 2**: Overwrite existing tags with new ones.
    * Pros: Simplifies data handling, avoids tag duplication.
    * Cons: Risk of losing previously assigned tags, less flexibility in tag management.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Aspect: Logic handling for pre-existing tags**

* **Alternative 1 (current choice)**: Do not add tags already present for the patient.
    * Pros: Prevents tag redundancy, maintains data integrity. Better user experience, do not need to worry about the intricacies of tag duplication.
    * Cons: Requires additional logic to detect repeated tags.
<br></br>
* **Alternative 2**: Return error message for duplicate tags.
    * Pros: Notifies user about duplicate inputs, ensures data consistency.
    * Cons: In the case of the addition of multiple existing or duplicate tags, users have to find and remove the duplicated tags from the given command, which would be cumbersome especially when there are many tags listed in the command.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.3 Deleting Tags From a Patient

#### Introduction

The `DeleteTagsCommand` class enables the removal of one or more tags from a patient in PatientSync.

#### Specifications

* Tags, as defined by the `Tag` class, are alphanumeric characters with or without spaces, and repeated tags in the command are added as a single tag.
* The deletion of tags is performed by specifying the tags to be removed for a particular patient.
* Tags should match exactly with the existing tags of the patient.
* If a patient has the tag(s) provided in the command, they will be removed. This operation is counted as a successful deletion.
* When deleting tags, if a tag is repeated in the command, it will be treated as a single tag to delete. E.g. `t/friend t/friend` will be considered as a single `friend` tag for deletion.
* If the patient does not have a tag provided in the command, it will be logged and shown to the user as an unsuccessful deletion of that tag.

The activity diagram below outlines the steps involved when a user initiates a Delete Tags command.
<puml src="diagrams/DeleteTagsActivityDiagram.puml" alt="DeleteTagsActivityDiagram" />

<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Below is an example scenario of how the tag deletion process works within the PatientSync application:

Step 1: The user accesses the PatientSync application.

Step 2: The user executes the `deletet 1 t/fall risk` command to delete the `fall risk` tag from patient 1 in the displayed patient list. The `DeleteTagsCommandParser` validates the input, ensuring that the index is valid and at least one tag is provided. Upon successful validation, an `DeleteTagsCommand` instance is created.

<box type="info" seamless>
<b>Note</b>: Since multiple inputs are allowed, a set of tags to be deleted is passed, each of which will be removed if found associated with the patient.
</box>

The following sequence diagram shows how the Delete Tags operation works:
<puml src="diagrams/DeleteTagsSequenceDiagram.puml" alt="DeleteTagsSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteTagsCommandParser` and `DeleteTagsCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>


#### Design Considerations

**Aspect: Bulk Tag Deletion**

* **Alternative 1 (current choice)**: Bulk deletion of specified tags.
    * Pros: Allows removal of multiple tags in one command, preserves existing tags if not specified for deletion.
    * Cons: Requires additional memory for handling tag sets, potentially slower performance for large tag sets.
      <br></br>
* **Alternative 2**: Explicitly specify tags to delete, ignoring any non-existent tags.
    * Pros: Simplifies command execution, faster performance for small tag sets.
    * Cons: Requires multiple commands for each tag deletion, less flexible in bulk operations.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Aspect: Handling Missing Tags**

* **Alternative 1 (current choice)**: Log output for non-existent tags to inform user, proceed with deleting the valid tags.
    * Pros: Simplifies user interaction, allows bulk deletion without worrying about non-existent tags, users are informed about the tags that are not present, Users do not need to correct the command.
    * Cons: Adds complexity to the command execution, requiring additional logic to differentiate between existing and non-existing tags.
      <br></br>
* **Alternative 2**: Return an error message for non-existent tags, ask users to correct the command.
    * Pros: Ensures user awareness of non-existent tags, avoids accidental deletions, prompts users to provide valid tag inputs.
    * Cons: Requires users to fix the command before proceeding, potential interruption to workflow, may increase user frustration if multiple tags are missing.

**Aspect: Feedback for Deletion Operation**

* **Alternative 1 (current choice)**: Provide a success message for each tag successfully deleted.
    * Pros: Clear indication of which tags were removed, better user understanding of command execution.
    * Cons: May clutter output for multiple tag deletions.
      <br></br>
* **Alternative 2**: Return a single success message for all successful tag deletions.
    * Pros: Cleaner output for multiple deletions, reduces command feedback clutter.
    * Cons: Users might not have a clear understanding of individual deletions, less granular feedback.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.4 Adding an Event to a Patient

#### Introduction

The `AddEventCommand` class is responsible for adding an Event to a patient in PatientSync.

#### Specifications

* Events, as defined by the `Event` class, contain both the Name of the Event that falls on that date, as well as the Date of the Event and optionally, the Time Period for which the Event is happening.

* The addition of Event is cumulative, and new Events will be added to the existing set of Events for the patient, preserving the previously assigned Events.

* If the patient already has a particular Event, it will not be added again.

The activity diagram below outlines the steps involved when a user initiates a Add Event command.
<puml src="diagrams/AddEventActivityDiagram.puml" alt="AddEventActivityDiagram" />

<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Given below is an example usage scenario and how the group creation mechanism behaves at each step.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes the `adde 1 n/Birthday d/20-01-2022` command to add the Event, Birthday, which falls on the 20th January.
* Upon successful validation, it creates an `AddEventsCommand` instance.

<box type="info" seamless>

<b>Note</b>: Only 1 Event can be added at a time per command

</box>

<puml src="diagrams/AddEventSequenceDiagram.puml" alt="Add Event Sequence Diagram" />

<box type="info" seamless>

<b>Note:</b> The lifeline for `AddEventCommandParser` and `AddEventCommand` should end at the destroy marker (X) but
due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

#### Design Considerations

**Aspect: Handling Repeated Events**

* **Alternative 1 (current choice)**: Repeated events are added as a single event.
    * Pros: Simplifies event management, avoids redundancy.
    * Cons: Requires additional logic to detect and merge repeated events.
      <br></br>
* **Alternative 2**: Each event is added individually, including duplicates.
    * Pros: Explicitly shows every event provided.
    * Cons: May clutter patient data with redundant events.

**Aspect: Cumulative Event Addition**

* **Alternative 1 (current choice)**: Cumulative addition of events to existing set.
    * Pros: Preserves previous events, allows for gradual building of patient profile.
    * Cons: Requires additional memory for storing updated events sets.
      <br></br>
* **Alternative 2**: Overwrite existing events with new ones.
    * Pros: Simplifies data handling, avoids events duplication.
    * Cons: Risk of losing previously assigned events, less flexibility in event management.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Aspect: Error Handling for Duplicate Events**

* **Alternative 1 (current choice)**: Do not add events already present for the patient.
    * Pros: Prevents event redundancy, maintains data integrity. Better user experience, do not need to worry about the intricacies of event duplication.
    * Cons: Users do not explicitly receive direct feedback about skipped events.
      <br></br>
* **Alternative 2**: Return error message for duplicate events.
    * Pros: Notifies user about duplicate inputs, ensures data consistency.
    * Cons: In the case of the addition of multiple existing or duplicate events, users have to find and remove the duplicated events from the given command, which would be cumbersome especially when there are many events listed in the command.


--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.5 Editing a Patient

#### Introduction

The `EditCommand` class is responsible for editing current patient's information in PatientSync.

#### Specifications

* EditCommand, as defined by the `EditCommand` class, contain parameters which consists of:
    *  `INDEX`: Positive integer, indicating the index of patient in the PatientSync list,
    *  `patientHospitalId`: String of non-negative numeric characters which uniquely identifies the patient,
    *  `name`, `preferredName`: String which contains alphanumeric characters and spaces,
    *  `foodPreference`, `familyCondition`, `hobby`, `tag`: String which are alphanumeric.
* All fields are optional in the EditCommand except for `INDEX`.
* Fields such as `foodPreference`, `familyCondition`, `hobby` and `tag` can be repeated for multiple inputs.
* If the fields for `patientHospitalId`, `name` and `preferredName` are repeated during the editing of patient, error message will be thrown.
* If the edited value of field is the same as the original value, nothing will be changed and it is allowed.

The activity diagram below outlines the steps involved when a user initiates a Edit command.
<puml src="diagrams/EditActivityDiagram.puml" alt="EditActivityDiagram" />

<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Given below is an example usage scenario and how the group creation mechanism behaves at each step.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes the `edit 2 f/Aglio-olio t/depression` command to edit an existing Patient whose index in 
the PatientSync is `2`, with changes on preferred food to be `Aglio-olio` and added a tag `depression`.

Step 3: The `EditCommandParser` will be called to validate the input, ensuring that the fields are valid with correct
data types and no duplicates of fields.
* Upon successful validation, it will update the `Patient` instance.

Step 4: The Patient with specified index will be updated in the list, shown in the UI.

The following UML sequence diagram illustrates how the `EditCommand` operation works.
<puml src="diagrams/EditSequenceDiagram.puml" alt="Edit Sequence Diagram" />

<box type="info" seamless>

<b>Note:</b> The lifeline for `EditCommandParser` and `EditCommand` should end at the destroy marker (X) but
due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

#### Design Considerations

**Aspect: Usage of Identifier**
* **Alternative 1 (current choice)**: Uses `INDEX` index of the Patient in the PatientSync.
    * Pros: Ease of use, as user can refer to the index in the PatientSync directly.
    * Cons: Referring and scrolling the PatientSync may take time to find the patient's index.
      <br></br>
*  **Alternative 2**: Uses `patientHospitalId` of a Patient.
    * Pros: Able to uniquely identified each patient.
    * Cons: Higher chance in typing the wrong `patientHospitalId`.

**Aspect: Bulk Edit for Certain Field**
* **Alternative 1 (current choice)**: Bulk edit a certain field.
  * Pros: User able to edit the field easily.
  * Cons: Input(s) that might be needed/ kept will be replaced by the newly edited ones.
    <br></br>
*  **Alternative 2**: Edit field by specifying the certain input needed to be changed.
  * Pros: Able to uniquely identified which specific input needed to be edited.
  * Cons: More time consuming to input command line as it will be longer. 
  
--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.6 Deleting an Event from a Patient

#### Introduction

The `DeleteEventCommand` class is responsible for deleting an Event from a patient in PatientSync.

#### Specifications

* DeleteEventCommand takes in two parameters: `PATIENT_INDEX` and `EVENT_INDEX` which are Indexes of patients
shown on the UI after using the `list` or `find` command and Indexes of the specified Patient's events as defined in
the `Index` class.

* Deletion of Event can only happen for a single patient, and a single event at any given time.

The activity diagram below outlines the steps involved when a user initiates a Delete Event command.
<puml src="diagrams/DeleteEventActivityDiagram.puml" alt="DeleteEventActivityDiagram" />

<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Given below is an example usage scenario and how the group creation mechanism behaves at each step.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes the `adde 1 n/ Birthday d/ 20-01-2022` command to add the Event, Birthday,
which falls on the 20th January.

* Upon successful validation, it creates an `AddEventsCommand` instance.

Step 3: The user executes `deletee 1 e/1` to delete the Event as the event is over.

* Upon successful validation,  an `DeleteEventCommand` instance is created.

The following UML sequence diagram illustrates how the Delete Event operation works.
<puml src="diagrams/DeleteEventSequenceDiagram.puml" alt="Delete Event Sequence Diagram" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteEventCommandParser` and `DeleteEventCommand` should end at the destroy marker (X) but
due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

#### Design Considerations

**Aspect: Choice of COMMAND_WORD**

* **Alternative 1 (current choice)**: Use `deletee`
    * Pros: Consistent with `adde` command to add new Event.
    * Cons: Might be counter-intuitive for user as command is unfamiliar.
      <br></br>
* **Alternative 2**: Use `deleteID`
    * Pros: Clearer syntax.
    * Cons: User might confuse ID as Patient ID and also inconsistency with `adde` command, further confusing user.

**Aspect: Syntax to choose event to delete**

* **Alternative 1 (current choice)**: Delete event by `EVENT_INDEX`. Syntax: prefix `e/` followed by `EVENT_INDEX` 
    * Pros: User do not need to type whole event name, also similar to delete patient where patient index is used to
  identify patient of interest.
    * Cons: User need to know the `EVENT_INDEX` of the patient.
      <br></br>
* **Alternative 2**: Delete event by `EVENT_NAME`. Syntax: prefix `e/` followed by `EVENT_NAME`
    * Pros: User can delete event quickly if name is short.
    * Cons: User need to input the whole event name which might be tedious if `EVENT_NAME` is very long.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.7 Editing an Event for a Patient

#### Introduction

The `EditEventCommand` class is responsible for editing a specific Event for a patient in PatientSync.

#### Specifications

* `EditEventCommand` takes in four parameters: `PATIENT_INDEX`, `EVENT_INDEX`, `NAME_OF_EVENT` 
  and `DATE_OR_DATETIME_OF_EVENT`. All parameters are compulsory. You may **exclude** `TIME` in the
  `DATE_OR_DATETIME_OF_EVENT` parameter.
* With `TIME`, an example of the `DATE_OR_DATETIME_OF_EVENT` is `20-12-2025, 12:00 - 15:00`.
* Without `TIME`, an example of the `DATE_OR_DATETIME_OF_EVENT` is `20-12-2025`.
* EditEventCommand will edit the selected `EVENT_INDEX` with a new event for the selected `PATIENT_INDEX`.
* Editing of an event can only happen for a single patient, and a single event at any given time.
* Editing an event to an existing event will not change the patient list as there should not have any duplicate
  events.

The activity diagram below outlines the steps involved when a user initiates an Edit Event command.
<puml src="diagrams/EditEventActivityDiagram.puml" alt="EditEventActivityDiagram" />

<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Given below is an example usage scenario and how the group creation mechanism behaves at each step.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes the `adde 1 n/Birthday d/20-12-2024` command to add the Event, Birthday,
which falls on the 20th December.
* Upon successful validation, it creates an `AddEventCommand` instance.

Step 3: The user executes `edite 1 e/1 n/New Birthday d/20-12-2025` to edit the Event.
* Upon successful validation,  an `EditEventCommand` instance is created.

The following UML sequence diagram illustrates how the Edit Event operations works.
<puml src="diagrams/EditEventSequenceDiagram.puml" alt="Edit Event Sequence Diagram" />

<box type="info" seamless>

**Note:** The lifeline for `EditEventCommandParser` and `EditEventCommand` should end at the destroy marker (X) but
due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>


#### Design Considerations

**Aspect: Choice of COMMAND_WORD**

* **Alternative 1 (current choice)**: Use `edite` 
    * Pros: Consistent with `adde` and `deletee` commands.
    * Cons: May not be as intuitive for user.
    <br></br>
* **Alternative 2**: Use `editID`
    * Pros: Clearer Syntax.
    * Cons: Inconsistent with `adde` and `deletee` commands.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.8 Deleting a Patient

#### Introduction

The `DeleteCommand` is responsible for deleting a patient in PatientSync.

#### Specifications

* Delete command is used when the user wants to remove a patient from PatientSync.

The activity diagram below outlines the steps involved when a user initiates a Delete command.
<puml src="diagrams/DeleteActivityDiagram.puml" alt="Delete Activity Diagram" />

<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Given below is an example usage scenario and how the group creation mechanism behaves at each step.

Step 1: The user accesses the PatientSync application.

Step 2: The user see all the patients in PatientSync.

Step 3: The user decide to remove the first patient in PatientSync.

Step 4: The user executes the `delete 1` command to remove the first patient in PatientSync.

The following UML sequence diagram illustrates how the Delete operations works.
<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Delete Sequence Diagram" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` and `DeleteCommand` should end at the destroy marker (X) but
due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

**Aspect: Choice of Index**
* **Alternative 1 (current choice)**: Use index shown in the PatientSync.
  * Pros: More user-friendly, as user can refer to the index in PatientSync directly.
  * Cons: The index of the patients in PatientSync might change due to some commands 
  such as `find`, `findt`, `sort` and `list`.
    <br></br>
* **Alternative 2**: Use `PATIENT_HOSPITAL_ID` of a Patient.
  * Pros: Able to uniquely identify each patient.
  * Cons: User might not remember the `PATIENT_HOSPITAL_ID` as it is not shown on the GUI.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.9 Listing all Patients

#### Introduction

The `ListCommand` is responsible for listing all patients in PatientSync.

####  Specifications

* The ListCommand make use of a `Predicate` that always evaluates to true.
* Through ListCommand#execute(), the `Predicate` is passed as an argument to Model#updateFilteredPersonList(),
causing the UI to only show all patients.
* The UML sequence diagram below shows the interaction between the Logic and Model components after calling `list`
command.
* For each Patient's Events, the Events will be displayed in ascending order by date, then start time if date is equal, 
then end time if both date and start time is equal

<puml src="diagrams/ListSequenceDiagram.puml" alt="List Sequence Diagram" />

<box type="info" seamless>

**Note:** The lifeline for `ListCommand` should end at the destroy marker (X) but
due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.10 Finding patients by name

#### Introduction

The `FindCommand` class is responsible for finding the patients by the name in the patient list
using keyword(s).

#### Specifications

* `FindCommand` takes in one or more keywords to find patients in the patient list.
* `FindCommand` will update the patient list with patients whose name matches the keyword(s).

The activity diagram below outlines the steps involved when a user initiates a Find command.
<puml src="diagrams/FindActivityDiagram.puml" alt="Find Activity Diagram" />

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Given below is an example usage scenario and how the group creation mechanism behaves at each step.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes `find Alex` to search for patients whose name is Alex.
* Upon successful execution, those patients whose name is `Alex` will be listed in the patient list.

The following UML sequence diagram illustrates how the Find operations works.
<puml src="diagrams/FindSequenceDiagram.puml" alt="Find Sequence Diagram" />

<box type="info" seamless>

**Note:** The lifeline for `FindCommandParser` and `FindCommand` should end at the destroy marker (X) but
due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

#### Design Considerations

**Aspect: Choice of keyword**

* **Alternative 1 (current choice)**: Search using `PATIENT_NAME` as the keyword
  * Pros: Easy for user to remember the name.
  * Cons: There may be many patients whose name contains the same keyword.
    <br></br>
* **Alternative 2**: Search using `PATIENT_HOSPITAL_ID` as the keyword
  * Pros: User may obtain the specific patient.
  * Cons: Hard for user to remember the specific `PATIENT_HOSPITAL_ID`.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.11 Finding patients by tag

#### Introduction

The `FindTagsCommand` class is responsible for finding the patients by their tag in the patient list
using keyword(s).

#### Specifications

* `FindTagsCommand` takes in one or more keywords to find patients using tag in the patient list.
* `FindTagsCommand` will update the patient list with patients whose tag(s) matches the keyword(s).

The activity diagram below outlines the steps involved when a user initiates a Find Tags command.
<puml src="diagrams/FindTagsActivityDiagram.puml" alt="Find Tags Activity Diagram" />

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Given below is an example usage scenario and how the group creation mechanism behaves at each step.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes `findt diabetes` to search for patients whose tag is diabetes.
* Upon successful execution, those patients whose tag is `diabetes` will be listed in the patient list.

The following UML sequence diagram illustrates how the Find Tags operations works.
<puml src="diagrams/FindTagsSequenceDiagram.puml" alt="Find Tags Sequence Diagram" />

<box type="info" seamless>

**Note:** The lifeline for `FindTagsCommandParser` and `FindTagsCommand` should end at the destroy marker 
(X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

#### Design Considerations

**Aspect: Choice of Command Structure**

* **Alternative 1 (current choice)**: Use `findt KEYWORD [MORE_KEYWORD]…​ `
  * Pros: Does not need to use tag prefix, and it is similar to `find` command.
  * Cons: Command structure is different from `addt` and `deletet`.
    <br><br>
* **Alternative 2**: Use `findt t/KEYWORD [t/MORE_KEYWORD]…​`
  * Pros: Command structure is similar to `addt` and `deletet`.
  * Cons: User need to key in multiple tag prefixes if they want to search with more keywords.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.12 Sorting patients by specified attributes

#### Introduction

The `SortCommand` class is responsible for sorting the patients by the specified attribute.

#### Specifications

* `SortCommand` takes in **zero** or **one** attribute to sort the patient list.
* `SortCommand` will update the patient list with the sorted patient list and the display the sorted patient list.
* If no attribute is specified, the default sorting method is by patient name.
* SortCommand only can sort by patient's name or patient's preferred name.
* If multiple patients have the same name, the original order (with respect to the affected patients only) will be
preserved for the affected patients.

The activity diagram below outlines the steps involved when a user initiates a Delete Event command.
<puml src="diagrams/SortActivityDiagram.puml" alt="SortActivityDiagram" />

<div style="page-break-after: always;"></div>

#### Example Usage Scenario

Given below is an example usage scenario and how the group creation mechanism behaves at each step.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes `sort p` to sort patient list by patient's preferred name.
* Upon successful execution, the patients will be sorted and the sorted patient list will be displayed.

The following UML sequence diagram illustrates how the Sort operations works.
<puml src="diagrams/SortSequenceDiagram.puml" alt="Sort Sequence Diagram" />

<box type="info" seamless>

**Note:** The lifeline for `SortCommandParser` and `SortCommand` should end at the destroy marker
(X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

#### Design Considerations

**Aspect: Choice of Command Structure**

* **Alternative 1 (current choice)**: Use `sort [ATTRIBUTE]`
  * Pros: Does not need to use prefix, and is easier and faster to type.
  * Cons: Command structure is different from other commands that uses prefix.
    <br><br>
* **Alternative 2**: Use `sort [PREFIX]`
  * Pros: Command structure is similar to other commands that uses prefix.
  * Cons: Command structure is weird and counter-intuitive.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Aspect: ATTRIBUTE specification**

* **Alternative 1 (current choice)**: Can be empty.
  * Pros: User can sort patient list by patient name quickly.
  * Cons: Need to conduct more checks in the sort command, and cannot choose default sorting method.
    <br><br>
* **Alternative 2**: Cannot be empty.
  * Pros: Fewer checks to implement in the sort command.
  * Cons: User needs to specify an attribute everytime, no default sorting method.

**Aspect: Sorting Algorithm**

* **Alternative 1 (current choice)**: Use default `Collections.sort` method with custom Comparator
  * Pros: No need to create complex sorting algorithms.
  * Cons: Might not be the most efficient sorting algorithm.
    <br><br>
* **Alternative 2**: Create new optimized sorting algorithm
  * Pros: Possibly more efficient and faster sorting.
  * Cons: Difficult to create a better sorting algorithm.

**Aspect: Method to update the model after sorting**

* **Alternative 1 (current choice)**: Create new `updatePatientList` method in Model
  * Pros: Updates the patient list with little code.
  * Cons: Need to ensure that the sorted patient list does not add or deletes patients accidentally, else
there will be extra or missing patients.
    <br><br>
* **Alternative 2**: Sort the patients, deletes all the patients in the patient list, then add the patients back in the
sorted order.
  * Pros: Easy and simple.
  * Cons: Slow and inefficient.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 3.13 Clearing all data in PatientSync

#### Introduction

The `ClearCommand` class is responsible for clearing all data in PatientSync.

#### Specifications

* `ClearCommand` requires no attributes or parameters.
* `ClearCommand` will delete all data and display an empty patient list.

#### Example Usage Scenario

Given below is an example usage scenario.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes `clear` to clear all data in PatientSync.
* Upon successful execution, an empty patient list will be displayed.

--------------------------------------------------------------------------------------------------------------------

### 3.14 Exiting PatientSync

#### Introduction

The `ExitCommand` class is responsible for exiting and closing PatientSync.

#### Specifications

* `ExitCommand` requires no attributes or parameters.

#### Example Usage Scenario

Given below is an example usage scenario.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes `exit` to exit PatientSync.
* Upon successful execution, PatientSync will close automatically and the process stopped.

--------------------------------------------------------------------------------------------------------------------

### 3.15 Viewing the User Guide 

#### Introduction

The `HelpCommand` class is responsible for opening a separate window to a link to the User Guide of PatientSync. 

#### Specifications

* `HelpCommand` requires no attributes or parameters.

#### Example Usage Scenario

Given below is an example usage scenario.

Step 1: The user accesses the PatientSync application.

Step 2: The user executes `help` to view PatientSync's User Guide.
* Upon successful execution, a separate window will be opened, with a link to PatientSync's User Guide that the user can click on to copy. 

--------------------------------------------------------------------------------------------------------------------

## 4 Planned Enhancements

Team size: 5

--------------------------------------------------------------------------------------------------------------------

### 4.1 Input Validation and Error Handling for Tags

Presently, the handling of potentially invalid inputs and the inability to detect incorrect flags for tags, such as when a user enters `/addtag` instead of the correct `/addt`  or incorrect tag formats (e.g., using `tag/TAG` instead of `t/TAG`) are limited. This can lead to errors and confusion for users, especially for new users.

To mitigate this, we are planning enhancements to include robust input validation, warnings for potential errors, and improved error messages. When an invalid input is detected, the system will provide even clearer feedback to the user, indicating the specific issue and suggesting corrective actions. This would include errors arising from missing index, and clearer distinctions between non-negative indexes and out of bounds indexes.

Additionally, an interactive command assistance feature will be introduced, offering real-time suggestions. These enhancements aim to improve the user experience, enhance system reliability, and bolster command comprehension within the PatientSync application.

--------------------------------------------------------------------------------------------------------------------

### 4.2 Edit tags feature
Currently, the process of modifying patient tags in the PatientSync application can be cumbersome, requiring users to delete and re-add tags individually. This can lead to potential errors, especially when handling a large number of tags or making multiple changes. 

To address this, we plan to introduce the EditTagsCommand feature, providing users with a more flexible and efficient way to manage patient tags. The planned EditTagsCommand feature is designed to enhance user productivity, reduce the likelihood of errors, and improve overall usability within PatientSync.

--------------------------------------------------------------------------------------------------------------------

### 4.3 Input Validation for Events

Presently, the Date and Datetime for Events, referred to as `DATE_OR_DATETIME_OF_EVENT`, do not have sufficient input validation for the validity of the `DATE_OR_DATETIME_OF_EVENT`. It currently uses `LocalDate.parse()` and `LocalTime.parse()` along with the pattern format, but these methods are not strict by default. For example, the user is currently able to input `30-02-2024, 24:00 - 24:00`. However, `30-02-2024` is not a valid Date and `24:00` is not a valid time. 

This can lead to potential errors if the user has accidentally mistyped the date and/or time when inputting the command, leading to confusion further down the line.

To address this, we plan to make our input validation for the `DATE_OR_DATETIME_OF_EVENT` field stricter, to ensure the validity of the values, and not just the format. Specifically, we intend to perform the following validations:

1. Ensure that the Date provided is valid
2. Ensure that the Time, if provided, ranges from `00:00` to `23:59`, inclusive

Upon identification of such invalid `DATE_OR_DATETIME_OF_EVENT` field values, PatientSync should then output a custom error message, i.e.,`Invalid DATE_OR_DATETIME_OF_EVENT!`

Note that `24:00` is accepted as it refers to the midnight corresponding to the instant at the end of the calendar day. This also results in a specific error evaluating whether the end time of the Event is before or equal to the start time of the Event as `24:00` evaluates to `00:00` when using `LocalTime.parse()`. Thus, an example Event with `DATE_OR_DATETIME_OF_EVENT` of `30-02-2024, 24:00 - 12:00` is accepted as valid. This error will also be resolved if `24:00` is not accepted in PatientSync.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 4.4 Addition of an Upper and Lower Bound for Event Date or Datetime

Presently, we do not restrict the user from adding events at any date. As such, the user is able to add Events for a Patient on unrealistic dates, i.e., 4000 years into the Future or Past. Adding such Events is more likely to be a typographical error and thus, we should warn the user (similar to how we warn the user for past events). We choose to warn the user rather than error on the command as it is possible, albeit rare, for a Person to live more than a 100 years.

To address this, we plan to introduce an Upper and Lower Bound for the Event Date, of approximately +- 100 years from the present year. Upon identification of `DATE_OR_DATETIME_OF_EVENT` with years outside of this range, PatientSync should then warn the user with a message to the effect of `Warning: This Event occurs more than a 100 years in the future / past`

--------------------------------------------------------------------------------------------------------------------

### 4.5 Case Sensitivity Duplicate Checks for Events

Presently, duplicate checks for Events are done via checking the Event Name (`NAME_OF_EVENT`), as well as Date / Datetime (`DATE_OR_DATETIME_OF_EVENT`). However, the duplicate check does not account for differences in case sensitivity of the `NAME_OF_EVENT`. For example, given the same `DATE_OR_DATETIME_OF_EVENT`, the Event `Birthday` and `birthday` would still be seen as two separate events. The user would potentially be able to thus create multiple duplicate events, which would clutter the UI and confuse the User.

To address this, we plan to make the Duplicate Check for Events case sensitive, such that we detect these scenarios. PatientSync would then also return a Success Result, albeit with the special Duplicate Message, to the user.

--------------------------------------------------------------------------------------------------------------------

### 4.6 Input Validation for Patient's Name

Presently, the name of patient, referred to as `NAME`, do not have sufficient input validation for the validity of the `NAME`.
PatientSync currently uses regex expression `[\\p{Alnum}][\\p{Alnum} ]*` to check that the user input for `NAME`, which is a String that consists of only alphanumeric characters and spaces, to validate the input for patient's name.
For example, the user is currently able to input `John Doe`. However, input such as `Abraham s/o Dahmil` or `Kenneth-David` is not a valid Name.

This can lead to potential troubles where user is not able to input patient's full name when necessary, but need to ignore the special characters during the Name insertion. 

To address this, we plan to make our input validation for the `NAME` field stricter, to ensure the validity of the NAME and cater all kinds of names.

Specifically, we intend to perform the following validations with special characters:

1. Ensure that the `NAME` field accepts special character `/` with specific string such as `s/o`, `d/o` and `w/o`
2. Ensure that the `NAME` field accepts special character `-` 
3. Ensure that the `NAME` field accepts special character `'`

Upon identification of such invalid `NAME` field values, PatientSync should then output a custom error message, i.e.,`Invalid NAME format!`

--------------------------------------------------------------------------------------------------------------------

### 4.7 Implementation on PatientHospitalId

Presently, the patient's hospital ID referred to as `PATIENT_HOSPITAL_ID`, is implemented as String.
PatientSync currently uses regex expression `^[0-9]+$` to check that the user input for `PATIENT_HOSPITAL_ID`, which is a String that consists only numeric characters to validate the input for patient's hospital ID.
For example, the user is currently able to input patient's hospital ID `22452`. However, input such as `000000` or overflow input `1234567898765456783434343` are allowed. 

This can lead to potential errors such as unlimited size of the input and input with all zeros. 

To address this, we plan to make our input validation for the `PATIENT_HOSPITAL_ID` field stricter by changing it to Integer, ensuring the validity of the patient's hospital ID. 

Specifically, we intend to perform the following validations:

1. Ensure that the `PATIENT_HOSPITAL_ID` field does not contain only zeros.
2. Ensure that the `PATIENT_HOSPITAL_ID` field have specific bound and length, such as having ID as numeric value ranging from 3 to 10 digits in length.

Upon identification of such invalid `PATIENT_HOSPITAL_ID` field values, PatientSync should then output a custom error message, i.e.,`Invalid PATIENT_HOSPITAL_ID!`

### 4.8 Displaying PatientHospitalId in UI 

Presently, we do not display `PATIENT_HOSPITAL_ID` field in the PatientSync UI. This is because the team do not want to clutter the UI with too much information as the `PATIENT_HOSPITAL_ID` is mainly used to check for duplicate patients.
However, user might find it confusing as `PATIENT_HOSPITAL_ID` is required upon adding new patient and allowed for editing, but not be able to see what is being inputted or edited.

To address this, we intend to introduce a new field in the PatientSync UI display, named `Patient Hospital ID`, to allow user to view patient's hospital ID.
Hence, this would be easier for user to check if there is any duplicated patient added/ edited to the PatientSync list. 

--------------------------------------------------------------------------------------------------------------------

### 4.9 Standardise Error Message for Index Out of Bounds Errors

Presently, when attempting run commands which requires an index value to be passed in, 2 possible error message are thrown:

1. `Invalid Command Format! ...`, when the index value provided is smaller than or equal to 0
1. `... index provided is invalid`, when the index value provided is greater than the total number stored in PatientSync

This may cause confusion, especially in the first scenario, as the user may mistakenly believe that the command they had inputted was erroneous due to the format, rather than the value.

To address this, we plan to adjust the validation checks currently in PatientSync, to ensure that the error message thrown is standardised for all Index Out of Bounds related errors. Specifically, we intent to standardise the error message to be that of `... index provided is invalid`, so that the user is better able to quickly identify and resolve the issue upon such an error occurring. 

--------------------------------------------------------------------------------------------------------------------

### 4.10 Implement a specific error message for invalid attributes in sort command

Presently, when an invalid sort attribute is provided by the user input, the error message will be:
* `Invalid Command Format! ...`

This may cause confusion as the user may mistakenly believe that the command they had inputted was erroneous due to the format, rather than the invalid sort attribute.

To address this, we plan to implement a specific error message for invalid attribute input by the user. Upon identification of invalid sort attribute, PatientSync should then output a custom error message, i.e `Invalid Sort Attribute! Please use sort attribute n or p only!`
This allows the user to understand that the sort attribute that they input was invalid and not the command format, preventing any confusion.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## 5 Documentation, logging, testing, configuration, dev-ops

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## 6 Appendix: Requirements

### 6.1 Product scope

**Target user profile**:

- has a need to manage a significant number of patients
- values comprehensive patient information for tailored treatment
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**:\
PatientSync is meticulously crafted for nurses who prioritize the well-being of their patients above all else. It allows nurses to input intimate details about their patients, such as food preferences and family conditions. This personalized approach enables nurses to deliver tailored care that meets the unique needs of each individual.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 6.2 User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​ | I want to …​                      | So that I can…​                                                                                                                          |
|----------|---------|-----------------------------------|------------------------------------------------------------------------------------------------------------------------------------------|
| `***`    | Nurse   | easily view the user guide        | learn more about the product and how to use whenever I need to                                                                           |
| `***`    | Nurse   | add patient's information         | add new patients and easily remember their preferences to make a personalized connection                                                 |
| `***`    | Nurse   | delete patient's information      | remove patients who have been discharged                                                                                                 |
| `***`    | Nurse   | list all patient's information    | easily find the details of my patients                                                                                                   |
| `***`    | Nurse   | add event for my patients         | keep track of my patients' appointments and see my overall schedule                                                                      |
| `***`    | Nurse   | delete event for my patients      | delete my patients' appointments if they are canceled                                                                                    |
| `***`    | Nurse   | add tags to my patients           | group the patients into categories                                                                                                       |
| `***`    | Nurse   | find patient with a specific tag  | quickly locate individuals with similar conditions, treatments, or requirements without having to scroll through the entire patient list |
| `***`    | Nurse   | save all previously added patients | ensure details of the patient would not be lost                                                                                          |
| `**`     | Nurse   | edit patient's information        | have the most updated information of my patients at all times                                                                            |
| `**`     | Nurse   | edit event for my patients        | edit my patients' appointments if they are changed                                                                                       |
| `**`     | Nurse   | delete tags from my patients      | delete the tag if it no longer applies                                                                                                   |
| `**`     | Nurse   | edit tags from my patients        | edit mistyped tags                                                                                                                       |
| `**`     | Nurse   | sort the patients by patient name | be flexible in how I want to view my patient list                                                                                        |
| `**`     | Nurse   | clear all data                    | start from a clean PatientSync                                                                                                           |
| `**`     | Nurse   | close PatientSync using commands  | fully interact with PatientSync from start to end using only commands                                                                    |



<div style="page-break-after: always;"></div>

### 6.3 Use cases

(For all use cases below, the **System** is `PatientSync` and the **Actor** is the `Nurse`, unless specified otherwise)

--------------------------------------------------------------------------------------------------------------------

**Use case: UC01 - Add a patient**

**MSS**

1. Nurse requests to add a patient.
1. PatientSync adds the patient and display success message.

    Use case ends.

**Extensions**

* 1a. Nurse's input is invalid command format.

    * 1a1. PatientSync shows an error message.
           
      Use case ends.
  
* 1b. Nurse's input contains invalid parameters value.

    * 1b1. PatientSync shows an error message. 
    
      Use case ends.

* 1c. Nurse's input contains duplicate patient hospital Id.

  * 1c1. PatientSync shows an error message.

    Use case ends.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Use case: UC02 - Delete a patient**

**MSS**

1. Nurse requests to list patients.
1. PatientSync shows a list of patients.
1. Nurse requests to delete a specific patient in the list.
1. PatientSync deletes the patient and displays success message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Nurse's input is invalid command format.

    * 3a1. PatientSync shows an error message.
    
      Use case resumes at step 2.

* 3b. Nurse's input contains invalid patient Id.

    * 3b1. PatientSync shows an error message.

      Use case resumes at step 2.

* 3c. The specified patient does not exist in the patient list.

  * 3c1. PatientSync shows an error message.

    Use case resumes at step 2.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Use case: UC03 - Edit a patient's information**

**MSS**

1. Nurse requests to list patients.
1. PatientSync shows a list of patients.
1. Nurse requests to edit a specific patient's information in the list.
1. PatientSync edits the patient information and displays success message.

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Nurse's input is invalid command format.

  * 3a1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3b. Nurse's input contains invalid patient Id.

  * 3b1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3c. The patient specified does not exist in the patient list.

  * 3c1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3d. Nurse's input contains invalid parameters value.

  * 3d1. PatientSync shows an error message.

    Use case resumes at step 2.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Use case: UC04 - List all patients**

**MSS**

1. Nurse requests to list all existing patients.
1. PatientSync shows a list of all existing patients and displays success message. 

   Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC05 - Add event for a patient**

**MSS**

1. Nurse requests to list patients.
1. PatientSync shows a list of patients.
1. Nurse requests to add an event for a specific patient in the list.
1. PatientSync adds an event for the patient and displays success message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Nurse input is invalid command format. 

    * 3a1. PatientSync shows an error message. 

      Use case resumes at step 2.

* 3b. The nurse input invalid patient index or invalid date time format for event.

    * 3b1. PatientSync shows an error message.

      Use case resumes at step 2.

* 3c. The patient specified does not exist in the patient list.

  * 3c1. PatientSync shows an error message.

    Use case resumes at step 2.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Use case: UC06 - Delete an event for a patient**

**MSS**

1.  Nurse requests to list patients.
1.  PatientSync shows a list of patients.
1.  Nurse requests to delete an event for a specific patient in the list.
1.  PatientSync deletes an event the patient and displays success message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Nurse's input is invalid command format.

    * 3a1. PatientSync shows an error message.

      Use case resumes at step 2.

* 3b. Nurse's input contains invalid patient index or invalid event index.

    * 3b1. PatientSync shows an error message.

      Use case resumes at step 2.

* 3c. The patient specified does not exist in the patient list.

  * 3c1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3d. The patient selected does not contain specified event.

  * 3d1. PatientSync shows an error message.

    Use case resumes at step 2.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Use case: UC07 - Edit an event for a patient**

**MSS**

1.  Nurse requests to list patients.
1.  PatientSync shows a list of patients.
1.  Nurse requests to edit an event for a specific patient in the list.
1.  PatientSync edits an event for the patient and displays success message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Nurse's input is invalid command format.

  * 3a1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3b. Nurse's input contains invalid patient index or event index.

  * 3b1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3c. The patient specified does not exist in the patient list.

  * 3c1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3d. Nurse's input contains invalid event name.

  * 3d1. PatientSync shows an error message.
    
    Use case resumes at step 2.

* 3e. Nurse's input contains invalid date or datetime format.
  
  * 3e1. PatientSync shows an error message.
    
    Use case resumes at step 2.

* 3f. Nurse's input contains duplicate event.

  * 3f1. PatientSync shows an error message.
    
    Use case resumes at step 2.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Use case: UC08 - Add tag(s) to a patient**

**MSS**

1. Nurse requests to list patients.
1. PatientSync shows a list of patients.
1. Nurse requests to add one or more tags to a specific patient in the list.
1. PatientSync adds the specified tag(s) to the patient and displays success message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Nurse's input is invalid command format.

    * 3a1. PatientSync shows an error message.

      Use case resumes at step 2.

* 3b. Nurse's input contains invalid patient index.

  * 3b1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3c. The patient specified does not exist in the patient list.

  * 3c1. PatientSync shows an error message.

    Use case resumes at step 2.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Use case: UC09 - Delete tag(s) from a patient**

**MSS**

1. Nurse requests to list patients.
1. PatientSync shows a list of patients.
1. Nurse requests to delete one or more tags from a specific patient in the list.
1. PatientSync deletes the specified tag(s) from the patient and displays success message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Nurse's input is invalid command format.

  * 3a1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3b. Nurse's input contains invalid patient index.

  * 3b1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3c. The patient specified does not exist in the patient list.

  * 3c1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3d. Nurse's input contains invalid tags.

  * 3d1. PatientSync shows an error message.

    Use case resumes at step 2.

* 3e. The patient selected does not contain specified tag.

  * 3e1. PatientSync shows an error message.
  
    Use case resumes at step 2.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Use case: UC10 - Find patients by tag(s)**

**MSS**

1. Nurse requests to list patients.
1. PatientSync shows a list of patients.
1. Nurse requests to find patients with a specific tag(s) in the list.
1. PatientSync finds patients with the specified tag(s) and displays success message.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Nurse's input is invalid command format.

    * 3a1. PatientSync shows an error message.

      Use case resumes at step 2.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC11 - Find patients by name**

**MSS**

1. Nurse requests to list patients.
1. PatientSync shows a list of patients.
1. Nurse requests to find patients with the specific name(s) in the list.
1. PatientSync finds patients with the specified name(s) and displays success message.

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. Nurse's input is invalid command format.

  * 3a1. PatientSync shows an error message.

    Use case resumes at step 2.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

**Use case: UC12 - Sort all existing patients**

**MSS**

1. Nurse requests to sort patients by specified attribute.
1. PatientSync sorts all existing patients.
1. PatientSync displays the sorted patient list and displays success message.

   Use case ends.

**Extensions**

* 1a. The nurse input invalid command format.

  * 1a1. PatientSync shows an error message.

    Use case ends.

* 1b. The nurse input for sort attribute is invalid.

  * 1b1. PatientSync shows an error message.

    Use case ends.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC13 - Request for help**

**MSS**

1. Nurse requests for help.
1. PatientSync shows pop up window with link to user guide and displays success message.
1. Nurse copies link from pop up window and access user guide with external browser.

   Use case ends.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC14 - Clearing all patients**

**MSS**

1. Nurse requests to clear all patients from the list.
1. PatientSync deletes all patients from the list and displays success message.

   Use case ends.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC15 - Closing PatientSync**

**MSS**

1. Nurse requests to exit PatientSync.
1. PatientSync closes.

   Use case ends.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 6.4 Non-Functional Requirements

1. Compatibility: Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Performance: Should be able to hold up to 1000 patients without a noticeable sluggishness in performance for typical usage.
3. Usability: A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Accessibility: Should operate without the need for internet access to fulfill its core purpose.
5. Ease of Use: Should be designed to be usable by a nurse new to patient management without extensive training.
6. Error Handling: Should provide clear, comprehensive error messages in plain language, guiding users on how to recover from errors due to incorrect inputs.
7. User Documentation: Should offer comprehensive, well-organized user documentation that guides users on how to effectively use PatientSync.
8. Developer Documentation: Should provide detailed developer documentation for those looking to enhance, customize, or develop extensions.

--------------------------------------------------------------------------------------------------------------------

### 6.5 Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## 7 Appendix: Instructions for manual testing

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

--------------------------------------------------------------------------------------------------------------------

### 7.1 Launch and window preferences

1. Initial launch

   1. Put the JAR file in an empty folder in which the app is allowed to create files (i.e., do not use a write-protected folder).
   1. Open a command window. Run the java -version command to ensure you are using Java 11. Do this again even if you did this before, as your OS might have auto-updated the default Java version to a newer version.

   1. Launch the jar file using the `java -jar` command rather than double-clicking (reason: to ensure the jar file is using the same java version that you verified above). Use double-clicking as a last resort.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

--------------------------------------------------------------------------------------------------------------------

### 7.2 Adding a patient

1. Add a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   1. Test case: `add id/ 54321 n/ John Doe p/ John f/ Curry chicken c/ Stable h/ Singing karaoke t/ amnesia`<br>
      Expected: New patient is added to the list. Status message shows details of the new patient.

   1. Other incorrect add commands to try: `add`, `add id/ 54321 h/ Singing karaoke`<br>
      Expected: Error message displayed.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 7.3 Deleting a patient

1. Deleting a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Patient List should not be empty.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

   1. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size)<br>
      Expected: Error message displayed.

--------------------------------------------------------------------------------------------------------------------

### 7.4 Listing all patients

1. Listing all patients

   1. Test case: `list`<br>
      Expected: All existing patients are shown in the list.

--------------------------------------------------------------------------------------------------------------------

### 7.5 Editing a patient

1. Editing a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Patient List should not be empty.

   1. Test case: `edit 1 p/Alex f/Fried rice`<br>
      Expected: The preferred name of the first patient is changed to Alex, food preference is changed to Fried rice.

   1. Other incorrect edit commands to try: `edit`, `edit 1 n/`<br>
      Expected: Error message displayed.

--------------------------------------------------------------------------------------------------------------------

### 7.6 Finding a patient by name

1. Finding a patient by name

   1. Prerequisites: List all patients using the `list` command. Patient List should not be empty.

   1. Test case: `find Alex`<br>
      Expected: All patients with the name Alex are shown in the list.
   
   1. Other incorrect find commands to try: `find`<br>
      Expected: Error message displayed.

--------------------------------------------------------------------------------------------------------------------

### 7.7 Adding tags to a patient

1. Adding tags to a patient

   1. Prerequisites: List all patients using the `list` command. Patient List should not be empty.

   1. Test case: `addt 1 t/depression`<br>
      Expected: The tag depression is added to the first patient.

   1. Other incorrect add tag commands to try: `addt 0`, `addt 1 t/`<br>
      Expected: Error message displayed.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 7.8 Deleting tags from a patient

1. Deleting tags from a patient

   1. Prerequisites: List all patients using the `list` command. Patient List should not be empty. The patient that is chosen should have tags.

   1. Test case: `deletet 1 t/diabetes`<br>
      Expected: The tag depression is deleted from the first patient.

   1. Other incorrect delete tag commands to try: `deletet 0`, `deletet 1 t/`<br>
      Expected: Error message displayed.

--------------------------------------------------------------------------------------------------------------------

### 7.9 Finding patients by tag

1. Finding patients by tag

   1. Prerequisites: List all patients using the `list` command. Patient List should not be empty. Chosen Tag should exist in at least one patient.

   1. Test case: `findt diabetes`<br>
      Expected: All patients with the tag diabetes are shown in the list.
   
    1. Other incorrect find tag command to try: `findt`<br>
      Expected: Error message displayed.

--------------------------------------------------------------------------------------------------------------------

### 7.10 Adding an event to a patient

1. Adding an event to a patient

   1. Prerequisites: List all patients using the `list` command. Patient List should not be empty.

   1. Test case: `adde 1 n/Family Visit d/30-09-2024, 12:00 - 15:00`<br>
        Expected: The event Family Visit is added to the first patient.

   1. Other incorrect add event commands to try: `adde 0`, `adde 1 n/Discharge d/20-02-2024, 11:00`<br>
      Expected: Error message displayed.

--------------------------------------------------------------------------------------------------------------------

### 7.11 Deleting an event from a patient

1. Deleting an event from a patient

   1. Prerequisites: List all patients using the `list` command. Patient List should not be empty. The patient that is chosen should have events.

   1. Test case: `deletee 1 e/1`<br>
      Expected: The first event of the first patient is deleted.

   1. Other incorrect delete event commands to try: `deletee 0`, `deletee 1 e/`<br>
      Expected: Error message displayed.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### 7.12 Editing an event for a patient

1. Editing an event for a patient

   1. Prerequisites: List all patients using the `list` command. Patient List should not be empty. The patient that is chosen should have events.

   1. Test case: `edite 1 e/1 n/Papa Birthday Celebration d/20-01-2025`<br>
      Expected: The first event of the first patient is edited to Papa Birthday Celebration.

   1. Other incorrect edit event commands to try: `edite 0`, `edite 1 e/`<br>
      Expected: Error message displayed.

--------------------------------------------------------------------------------------------------------------------

### 7.13 Sorting the patient list

1. Sorting the patient list

   1. Prerequisites: Patient List should not be empty.

   1. Test case: `sort p`<br>
      Expected: The patient list is sorted by patient's preferred name.

   1. Other incorrect sort command to try: `sort name`, `sort 1`<br>
      Expected: Error message displayed.
