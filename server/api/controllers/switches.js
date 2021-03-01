import Switch from "../models/Switch.js";
import mongoose from "mongoose";

export function switches_get_switch(req, res, next) {
  Switch.findById(req.params.switchId)
    .select("_id name status countFlips")
    .exec()
    .then((aSwitch) => {
      if (aSwitch) {
        res.status(200).json({
          switch: aSwitch,
          request: {
            type: "GET",
            url: "http://localhost:3000/switches",
          },
        });
      } else {
        res.status(404).json({ message: "Switch not found" });
      }
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}

export function switches_create_switch(req, res, next) {
  const aSwitch = new Switch({
    _id: mongoose.Types.ObjectId(),
    name: req.body.name,
    status: req.body.status,
    countFlips: req.body.countFlips,
  });
  aSwitch
    .save()
    .then((result) => {
      res.status(200).json({
        message: "Saved!!",
        switch: {
          name: result.name,
          status: result.status,
          countFlips: result.countFlips,
        },
      });
    })
    .catch((err) => {
      console.log(err);
      res.status(500).json({ error: err });
    });
}

export function switches_get_all(req, res, next) {
  Switch.find()
    .select("_id name status countFlips")
    .exec()
    .then((docs) => {
      res.status(200).json({
        count: docs.length,
        rooms: docs.map((doc) => {
          return {
            _id: doc._id,
            name: doc.name,
            status: doc.status,
            countFlips: doc.countFlips,
            request: {
              type: "GET",
              url: "http://localhost:3000/rooms/" + doc._id,
            },
          };
        }),
      });
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}
/*
export function rooms_create_room(req, res) {
  const room = new Room({
    _id: mongoose.Types.ObjectId(),
    name: req.body.name,
  });
  room
    .save()
    .then((result) => {
      res.status(200).json({
        message: "Saved!!",
        room: {
          name: result.name,
        },
      });
    })
    .catch((err) => {
      console.log(err);
      res.status(500).json({ error: err });
    });
}

export function rooms_get_room(req, res, next) {
  Room.findById(req.params.roomId)
    .select("_id name")
    .exec()
    .then((room) => {
      if (room) {
        res.status(200).json({
          room: room,
          request: {
            type: "GET",
            url: "http://localhost:3000/rooms",
          },
        });
      } else {
        res.status(404).json({ message: "Room not found" });
      }
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}

export function rooms_delete_room(req, res, next) {
  Room.findById(req.params.roomId)
    .then((room) => {
      if (room) {
        res.status(200).json({
          message: "Room Deleted!!",
          room: room,
          request: {
            type: "GET",
            url: "http://localhost:3000/rooms",
          },
        });
        room.deleteOne();
      } else {
        res.status(404).json({ message: "Room not found" });
      }
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}

export function rooms_change_room(req, res, next) {
  const id = req.params.roomId;
  const updateOps = {};
  for (const ops of req.body) {
    updateOps[ops.propName] = ops.value;
  }
  Room.updateOne({ _id: id }, { $set: updateOps })
    .exec()
    .then((result) => {
      console.log(result);
      res.status(200).json({
        message: "Room Updated!!",
        updatedRoom: { result },
      });
    })
    .catch((err) => {
      console.log(err);
      res.status(500).json({ error: err });
    });
}
*/
