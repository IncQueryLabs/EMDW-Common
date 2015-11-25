#ifndef __XUMLRT__STRING_UTIL_HH
#define __XUMLRT__STRING_UTIL_HH

#include <string>
#include <sstream>
#include <regex>

namespace eatf{
namespace ComponentsLibrary{
namespace EATF{
namespace eatfImplPkg{
namespace string_utils {

    inline void replace_all(std::string& str, const std::string& from, const std::string& to) {
        if(from.empty())
            return;
        ::std::regex pattern {from};
        str = ::std::regex_replace(str, pattern, to);
    }

} /* namespace string_utils */
} /* namespace eatfImplPkg */
} /* namespace EATF */
} /* namespace ComponentsLibrary */
} /* namespace eatf */

#endif
