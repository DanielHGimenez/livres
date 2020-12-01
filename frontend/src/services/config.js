import axios from "axios";

export const http = axios.create({
  baseURL: "http://livresbs.ga:8080/",
});
