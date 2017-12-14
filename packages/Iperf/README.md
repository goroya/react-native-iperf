
# react-native-iperf

## Getting started

`$ npm install react-native-iperf --save`

### Mostly automatic installation

`$ react-native link react-native-iperf`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-iperf` and add `RNIperf.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNIperf.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNIperfPackage;` to the imports at the top of the file
  - Add `new RNIperfPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-iperf'
  	project(':react-native-iperf').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-iperf/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-iperf')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNIperf.sln` in `node_modules/react-native-iperf/windows/RNIperf.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Iperf.RNIperf;` to the usings at the top of the file
  - Add `new RNIperfPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNIperf from 'react-native-iperf';

// TODO: What to do with the module?
RNIperf;
```
  