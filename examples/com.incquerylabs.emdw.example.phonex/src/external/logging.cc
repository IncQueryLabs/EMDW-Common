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

