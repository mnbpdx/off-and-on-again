import express from "express";
const router = express.Router();

import {
  switches_get_switch,
  switches_create_switch,
  switches_get_all,
  switches_update_switch,
} from "../controllers/switches.js";

router.get("/:switchId", switches_get_switch);

router.post("/", switches_create_switch);

router.get("/", switches_get_all);

router.patch("/:switchId", switches_update_switch);

export default router;
