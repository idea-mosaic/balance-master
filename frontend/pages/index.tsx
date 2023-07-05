import Button from "@/components/Button";

export default function Home() {
  return (
    <>
    <p>버튼 컴포넌트 테스트</p>
    <span>등록, 수정 버튼</span>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"등록"}
        color={"main-color3"}
        size={"sm-btn"}
      ></Button>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"수정"}
        color={"main-color3"}
        size={"sm-btn"}
      ></Button>
      <br />
      <br />
      <span>조건 추가하기 버튼</span>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"조건 추가하기"}
        color={"main-color1"}
        size={"lg-btn"}
      ></Button>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"조건 추가하기"}
        color={"main-color2"}
        size={"lg-btn"}
      ></Button>
      <br />
      <br />
      <span>댓글 저장하기 버튼</span>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"저장하기"}
        color={"main-color1"}
        size={"lg-btn"}
      ></Button>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"저장하기"}
        color={"main-color2"}
        size={"lg-btn"}
      ></Button>
      <br />
      <br />
      <span>댓글 수정/삭제 버튼</span>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"수정"}
        color={"transparent"}
        size={"xs-btn"}
      ></Button>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"삭제"}
        color={"transparent"}
        size={"xs-btn"}
      ></Button>
      <br />
      <br />
      <span>투표하기, 투표 만들기 버튼</span>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"투표하기"}
        color={"transparent"}
        size={"md-btn"}
      ></Button>
      <Button
        clickEvent={function (): void {
          alert("CLICKED!!");
        }}
        content={"투표 만들기"}
        color={"transparent"}
        size={"md-btn"}
      ></Button>
    </>
  );
}
