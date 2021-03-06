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
 * NAME: ARCH
 *
 *
 *
 ******************************************************************************/

#include "PhoneX/ComponentLibrary/PhoneX/ImplementationPkg/ARCH.hh"
#include "PhoneX/ComponentLibrary/PhoneX/PhoneX_def.hh"
#include <cstdlib>

void (::PhoneX::ComponentLibrary::PhoneX::ImplementationPkg::ARCH::shutdown)() {
	exit(0);
}
