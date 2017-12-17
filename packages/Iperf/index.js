
import { NativeModules,  DeviceEventEmitter } from 'react-native';
const { RNIperf } = NativeModules;
RNIperf.on = (eventName, cb) => {
    DeviceEventEmitter.addListener(eventName, cb);
};
RNIperf.EVENT = {
    "START": "start",
    "OUTPUT": "output",
    "CANCEL": "cancel",
    "END": "end",
    "ERROR": "error",
}

export default RNIperf;
