// import { Socket } from "dgram";
import { createServer } from "http";
// import { Server } from "socket.io";
import app from "./app.js";
import { Server } from "socket.io";

const port = process.env.port || 5000;

const server = createServer(app);
const io = new Server(server);

io.on("connection", (socket) => {
  console.log("We have a new connection!!!");

  socket.on("disconnect", () => {
    console.log("User has left!!!");
  });
});

server.listen(port);
