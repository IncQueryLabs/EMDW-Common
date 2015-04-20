/******************************************************************************
 *
 * O B J E C T   C L A S S   ( H E A D E R )
 *
 * NAME: Test Class
 *
 * NUMBER: 1
 *
 * KEY LETTERS: TEST
 *
 *
 * Dummy Skeleton for State Machine generator testing
 *
 ******************************************************************************/

#ifndef __TEST_FSM__MAIN_PACKAGE__TEST_COMPONENT__TEST_PACKAGE__TEST_HH
#define __TEST_FSM__MAIN_PACKAGE__TEST_COMPONENT__TEST_PACKAGE__TEST_HH

#ifndef __EMC_CPP_BUILD
#error Generated header file Z:/home/jenkins/wss/BP4110/ws1/Test_FSM/gen/CPP_Linux\Test_FSM\Main_Package\Test_Component\TEST.hh is not accessible from the handwritten source code.
#else
#if __EMC_CPP_BUILD!=456
#error Header file Z:/home/jenkins/wss/BP4110/ws1/Test_FSM/gen/CPP_Linux\Test_FSM\Main_Package\Test_Component\TEST.hh was generated by a different version of EMC-C++.
#endif
#endif

#include <list>
#include <queue>
#include <string>


#include "Test_FSM/Main_Package/Test_Component/Test_Component_decl.hh"

namespace Test_FSM {
  namespace Main_Package {
    namespace Test_Component {
      namespace Test_Package {
        class TEST {
        public:
          enum TEST_state {TEST_STATE_INIT, TEST_STATE_WORKING};
          enum TEST_event {TEST_EVENT_WORK, TEST_EVENT_DONE, TEST_EVENT_NOP};

          // Constructor
          TEST();

          // Destructor
          ~TEST();

          // Attributes
          TEST_state current_state;
          std::string name;

          // int instead of TEST_EVENT since we probably have no idea it is an event we can process
          void processEvent(int eventId, std::string eventContent);

        private:
          // INIT state
          void processEventInInitState(int eventId, std::string eventContent);

          bool evaluateGuardOnInitToWorkingTransition(int eventId, std::string eventContent);

          // WORKING state
          void processEventInWorkingState(int eventId, std::string eventContent);


          void generateEvent(int eventId, std::string eventContent);


        };
      }
    }
  }
}

#endif
