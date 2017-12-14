using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Iperf.RNIperf
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNIperfModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNIperfModule"/>.
        /// </summary>
        internal RNIperfModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNIperf";
            }
        }
    }
}
