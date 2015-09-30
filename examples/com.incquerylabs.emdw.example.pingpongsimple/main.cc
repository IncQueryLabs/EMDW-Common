/******************************************************************************
 * 
 * P I N G - P O N G   S I M P L E   E X A M P L E
 * 
 * NAME: main.cc
 * 
 ******************************************************************************/

#include "PingPongSimple/Component/Component_def.hh"

int main(int , char **) {
    using namespace ::PingPongSimple::Component;

    Ping* pi = new Ping();
    Pong* po = new Pong();
    Component* comp = Component::get_instance();

    pi->R1_pong = po;
    po->R1_ping = pi;

    pi->perform_initialization();
    po->perform_initialization();

    for (int i = 0; i < 20; ++i) {
        comp->process();
    }

    delete pi;
    delete po;
}
