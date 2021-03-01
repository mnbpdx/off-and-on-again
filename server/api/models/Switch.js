import mongoose from "mongoose";

const switchSchema = mongoose.Schema({
  _id: mongoose.Schema.Types.ObjectId,
  name: { type: String, required: true },
  isOn: { type: Boolean, required: true },
  countFlips: { type: Number, default: 0 },
});

export default mongoose.model("Switch", switchSchema);
