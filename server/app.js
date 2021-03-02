import express from "express";
const app = express();
import morgan from "morgan";
import bodyParser from "body-parser";
import mongoose from "mongoose";

// Import Routes Here
import switchRoutes from "./api/routes/switches.js";

mongoose.connect(
  "mongodb+srv://mbeebepdx:" +
    process.env.MONGO_ATLAS_PW +
    "@cluster0.nvoye.mongodb.net/off-and-on-again-server?retryWrites=true&w=majority",
  {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useFindAndModify: false,
    useCreateIndex: true,
  }
);
Promise = global.Promise;

app.use(morgan("dev"));
// app.use("/uploads", static("uploads"));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept, Authorization"
  );
  if (req.method === "OPTIONS") {
    res.header("Access-Control-Allow-Methods", "PUT, POST, PATCH, DELETE, GET");
    return res.status(200).json({});
  }
  next();
});

// // Routes to handle requests
app.use("/switches", switchRoutes);

// app.get("/", (req, res) => {
//   console.log("Get Reqest called");
// });

// Handles requests that don't correspond to routes
app.use((req, res, next) => {
  const error = new Error("Route Not found");
  error.status = 404;
  next(error);
});

// Handles errors the server itself throws
app.use((error, req, res, next) => {
  res.status(error.status || 500);
  res.json({
    error: {
      message: error.message,
    },
  });
});

export default app;
