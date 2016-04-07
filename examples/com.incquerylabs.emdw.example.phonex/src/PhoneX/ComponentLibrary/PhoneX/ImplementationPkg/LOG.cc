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
 * E X T E R N A L   B R I D G E   B O D Y
 *
 * NAME: LOG
 *
 *
 *
 ******************************************************************************/

#include "PhoneX/ComponentLibrary/PhoneX/ImplementationPkg/LOG.hh"
#include "PhoneX/ComponentLibrary/PhoneX/PhoneX_def.hh"

#include "external/logging.hh"

void (::PhoneX::ComponentLibrary::PhoneX::ImplementationPkg::LOG::LogDate)(long d, ::std::string message) {
    std::cout << "EATF:" << "DATE: " << d << " - " << message << std::endl;
}
void (::PhoneX::ComponentLibrary::PhoneX::ImplementationPkg::LOG::LogFailure)(::std::string message) {
    ::xumlrt::logging::log_failure(message);
}
void (::PhoneX::ComponentLibrary::PhoneX::ImplementationPkg::LOG::LogInfo)(::std::string message) {
    ::xumlrt::logging::log_info(message);
}
void (::PhoneX::ComponentLibrary::PhoneX::ImplementationPkg::LOG::LogInteger)(long message) {
    ::xumlrt::logging::log_integer(message);
}
void (::PhoneX::ComponentLibrary::PhoneX::ImplementationPkg::LOG::LogReal)(double r, ::std::string message) {
    ::xumlrt::logging::log_real(message, r);
}
void (::PhoneX::ComponentLibrary::PhoneX::ImplementationPkg::LOG::LogSuccess)(::std::string message) {
    ::xumlrt::logging::log_success(message);
}
void (::PhoneX::ComponentLibrary::PhoneX::ImplementationPkg::LOG::LogTime)(long t, ::std::string message) {
    std::cout << "EATF:" << "TIME: " << t << " - " << message << std::endl;
}
