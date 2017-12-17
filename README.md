# react-native-iperf

## Summary
[en]

This is iperf wrapper on react native.
Currently under development.

[ja]

これはReact NativeのIperfラッパです。
現在、開発中です。
## request
[en]

Please tell me how to build iperf3 for ios.
I am considering ios support in this library.

[ja]

iOSでのiperf3のビルド方法を教えてほしい。
わたしはこのライブラリのiOSサポートを検討している。

## Example

```js
await Iperf.init();
Iperf.on(Iperf.EVENT.START, (e) => { console.log("Iperf START", e); });
Iperf.on(Iperf.EVENT.OUTPUT, (e) => { console.log("Iperf OUTPUT", e); });
Iperf.on(Iperf.EVENT.CANCEL, (e) => { console.log("Iperf CANCEL", e); });
Iperf.on(Iperf.EVENT.END, (e) => { console.log("Iperf END", e); });
Iperf.on(Iperf.EVENT.ERROR, (e) => { console.log("Iperf ERROR", e); });
await Iperf.start("-s -p 2000");
await Iperf.cancel();
```