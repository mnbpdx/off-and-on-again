import Switch from "../models/Switch.js";
import mongoose from "mongoose";
import { io } from "../../server.js";

export function switches_get_switch(req, res, next) {
  Switch.findById(req.params.switchId)
    .select("_id name isOn countFlips")
    .exec()
    .then((aSwitch) => {
      if (aSwitch) {
        res.status(200).json({
          switch: aSwitch,
          request: {
            type: "GET",
            url: "http://localhost:5000/switches",
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
    isOn: req.body.isOn,
    countFlips: req.body.countFlips,
  });
  aSwitch
    .save()
    .then((result) => {
      res.status(200).json({
        message: "Saved!!",
        switch: {
          name: result.name,
          isOn: result.isOn,
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
    .select("_id name isOn countFlips")
    .exec()
    .then((docs) => {
      res.status(200).json({
        count: docs.length,
        switches: docs.map((doc) => {
          return {
            _id: doc._id,
            name: doc.name,
            isOn: doc.isOn,
            countFlips: doc.countFlips,
            request: {
              type: "GET",
              url: "http://localhost:5000/switches/" + doc._id,
            },
          };
        }),
      });
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}

export function switches_update_switch(req, res, next) {
  const id = req.params.switchId;
  Switch.findById(id)
    .exec()
    .then((aSwitch) => {
      if (aSwitch) {
        aSwitch.countFlips++;
        aSwitch.isOn = !aSwitch.isOn;
        aSwitch.save();
        res.status(200).json({
          message: "Switch Updated!!",
          updatedSwitch: { aSwitch },
        });
        io.emit("switch-response", aSwitch);
      } else {
        res.status(404).json({ message: "Switch not found" });
      }
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}
