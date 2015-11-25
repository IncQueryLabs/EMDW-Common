#ifndef __xumlrt__LOGGING_HH
#define __xumlrt__LOGGING_HH

#include <string>


namespace xumlrt {
  namespace logging {
    void log_failure(std::string message);
    void log_info(std::string message);
    void log_integer(int message);
    void log_real(std::string message, double r);
    void log_success(std::string message);
  };
}

#endif
