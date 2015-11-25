# PhoneX example

## Running the c++ code
To build and run the generated code, copy the provided `src` folder into the generated
project and override conflicting files. This folder contains the code for
external entities, a custom `Makefile` to include these external classes to the build
and a `main.cc` which runs the tests provided in the model.

## Description
This example models a simplified telecommunication system component, which is
responsible for the registration and unregistration of users and managing
calls between the registered users.

The component can receive requests (and respond to these requests) through a
PhoneXPort instance and it can communicate with the database through a
DBPort instance.

The registered users are saved to the database - the database entries are
represented by UserMock instances in the model.

### Structure of the model
The main part of the model consists of
* the different **roles** a subscriber can have
  * Called
  * Caller
  * RegisteredSubscriber
  * UnregisteredSubscriber
* the **processes** of the component
  * Call
  * RegistrationProcess
  * UnregistrationProcess
* the **service** running these processes

Additionally the model contains
* external entities for often used functionalities (LOG, TIM, ARCH)
* classes for running test cases (SEQ, SEQ_TESTCASE, SEQ_BUCKET, functions, testfunctions)
* test cases for usual scenarios (TB_TESTCASE, SuccessfulRegistration, UnSuccessfulRegistration,
  NormalCall, UnknownNumber, UserBusy)

### Behavior of the model
When the component receives a request the PhoneXPort creates the corresponding process
and forwards the details of the request to it. The processes work with users in specific
roles rather than bare user data. These "users with a role" objects are created (based on the
request or data from the database) and deleted by the processes. After the termination of the
process it sends the result of the operation to the user of the component through the PhoneXPort.

_Note:_ currently the outer world is simulated by the test cases, and the PhoneXPort is implemented
to communicate with them directly.

##### Example
If the component receives a request for placing a call a Call process is created and
the phone numbers of the caller and the called are passed to it. The Call process then
queries the database for registered users, and creates the Caller and Called objects for them.
When the call is terminated both the Caller and the Called objects are deleted, but the registered
users remain available in the database.
