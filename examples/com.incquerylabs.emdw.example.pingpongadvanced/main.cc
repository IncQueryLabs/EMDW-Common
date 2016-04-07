/*******************************************************************************
 * Copyright (c) 2015-2016 IncQuery Labs Ltd. and Ericsson AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Akos Horvath, Abel Hegedus, Zoltan Ujhelyi, Daniel Segesdi, Tamas Borbas, Robert Doczi, Peter Lunk - initial API and implementation
 *******************************************************************************/
/******************************************************************************
 * 
 * P I N G - P O N G   A D V A N C E D   E X A M P L E
 * 
 * NAME: main.cc
 * 
 ******************************************************************************/

#include "PingPongAdvanced/Component/Component_def.hh"

int main(int , char **) {
    using namespace ::PingPongAdvanced::Component;

    Ping* pi = new Ping();
    Pong* po = new Pong();
    Component* comp = Component::get_instance();

    pi->R1_pong = po;
    po->R1_ping = pi;


    pi->perform_initialization();
    po->perform_initialization();

    for (int i = 0; i < 25; ++i) {
        comp->process();
    }

    delete pi;
    delete po;
}
