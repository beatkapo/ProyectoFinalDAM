// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyCYWeHCNmhr6c3lQ6m3t-YKwfVq8B27g6o",
  authDomain: "gestion-de-pedidos-63648.firebaseapp.com",
  projectId: "gestion-de-pedidos-63648",
  storageBucket: "gestion-de-pedidos-63648.appspot.com",
  messagingSenderId: "140919238944",
  appId: "1:140919238944:web:8e6d7990195d47e358a745",
  measurementId: "G-61ZLTTWD7Y"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);