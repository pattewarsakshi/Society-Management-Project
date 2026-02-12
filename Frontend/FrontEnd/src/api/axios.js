// import axios from "axios";

// const api = axios.create({
//   baseURL: "/",
//   withCredentials: true,
// });

// export default api;
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
  withCredentials: true   // 🔥 THIS IS CRITICAL
});

export default api;


