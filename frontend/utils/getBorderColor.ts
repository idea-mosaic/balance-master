import { BORDER_COLORS } from "@/constants/color";

export const getBorderColor = (color: "red" | "blue" | undefined): string => {
  return color ? BORDER_COLORS[color] : "border-mainColor3";
};
