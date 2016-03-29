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
#include "logging.hh"

#include <iostream>
#include <time.h>
#include <sys/time.h>

using std::string;
using std::cout;
using std::endl;

void xumlrt::logging::log_failure(string message) {
  cout<<""<<"FAILURE: "<<message<<endl;
}

void xumlrt::logging::log_info(string message) {
  cout<<""<<"INFO: "<<message<<endl;
}

void xumlrt::logging::log_integer(int message) {
  cout<<""<<message<<endl;
}

void xumlrt::logging::log_real(string message, double r) {
  cout<<""<<r<<" "<<message<<endl;
}

void xumlrt::logging::log_success(string message) {
  cout<<""<<"SUCCESS: "<<message<<endl;
}

