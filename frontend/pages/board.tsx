import BoardItem from "@/components/BoardItem";

function board() {
  return (
    <div className="flex flex-col gap-8 px-16 pt-4">
      <BoardItem
        redImage="https://i.namu.wiki/i/zLCSMHVvvt9_ZJyb33kImeEfmrBewsgNll9AyPyVcyNNX2frg1svs7I-nrm-seExLN7BcJ__RJMJCGKDIUkgPg.webp" 
        blueImage="https://recipe1.ezmember.co.kr/cache/recipe/2017/10/22/aaeb2a235b89ac305ba919e33da2e6331.jpg" 
        redTitle="짜장"
        blueTitle="짬뽕"
        title="짜장 vs 짬뽕 당신의 선택은?!" author="송히" createDate="2023-07-06 19:04"
      ></BoardItem>
      <BoardItem
        redTitle="짜장면"
        blueTitle="짬뽕면"
        title="긴 제목으로 적으면 쩜쩜쩜으로 표시되는지 확인 좀 할게용!!!" author="송히" createDate="2023-07-06 19:04"
      ></BoardItem>
      <BoardItem
        redTitle="짜장면짜장면짜장면짜장면짜장면"
        blueTitle="짬뽕면짬뽕면짬뽕면짬뽕면짬뽕면"
        title="긴 제목으로 적으면 쩜쩜쩜으로 표시되는지 확인 좀 할게용!!!" author="송히" createDate="2023-07-06 19:04"
      ></BoardItem>
    </div>
  );
}

export default board;
