/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */
import './ReactotronConfig'
import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View
} from 'react-native';
import Iperf from 'react-native-iperf';

import Reactotron from 'reactotron-react-native'


const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});
export default class App extends Component<{}> {
  async componentDidMount() {
    Reactotron.log("Didmount");
    try {
       await Iperf.init();
       console.log("AAAA")
       Iperf.on(Iperf.EVENT.START, (e) => { console.log("Iperf START", e); });
       Iperf.on(Iperf.EVENT.OUTPUT, (e) => { console.log("Iperf OUTPUT", e); });
       Iperf.on(Iperf.EVENT.CANCEL, (e) => { console.log("Iperf CANCEL", e); });
       Iperf.on(Iperf.EVENT.END, (e) => { console.log("Iperf END", e); });
       Iperf.on(Iperf.EVENT.ERROR, (e) => { console.log("Iperf ERROR", e); });
       await Iperf.start("-c 192.168.11.4 -p 2000");
       //await Iperf.start("-s -p 2000");
       //setTimeout(() => { console.log("cancel"); Iperf.cancel();}, 5000)
    } catch (e) {
      console.error("init Error", e)
    }
  }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit App.js
        </Text>
        <Text style={styles.instructions}>
          {instructions}
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
