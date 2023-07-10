import CandidateVote from "@/components/CandidateVote";
import Comment from "@/components/Comment";
import Image from "next/image";
import { useState } from "react";

function vote() {
  const [result, setResult] = useState<{
    redRatio: number;
    blueRatio: number;
  }>();
  const game = {
    red: "짜장",
    blue: "짬뽕",
    redDescription: "중국집하면 짜장면이지!",
    blueDescription: "매운 짬뽕이 짱임!",
    blueImg:
      "https://recipe1.ezmember.co.kr/cache/recipe/2017/10/22/aaeb2a235b89ac305ba919e33da2e6331.jpg",
    redImg: undefined,
  };
  const getResult = {
    redRatio: 36,
    blueRatio: 64,
  };
  const handleClick = () => {
    setResult(getResult);
  };
  return (
    <div className="px-8">
      <div className="flex gap-32">
        <CandidateVote
          color="red"
          title={game.red}
          desc={game.redDescription}
          image={game.redImg}
          ratio={result?.redRatio}
          clickEvent={handleClick}
        />
        <CandidateVote
          color="blue"
          title={game.blue}
          desc={game.blueDescription}
          image={game.blueImg}
          ratio={result?.blueRatio}
          clickEvent={handleClick}
        />
      </div>
      <Image
        src="/assets/VSIcon.svg"
        alt="vs"
        height={80}
        width={80}
        className="absolute top-80 left-1/2 -translate-x-1/2"
      />
    </div>
  );
}

export default vote;
