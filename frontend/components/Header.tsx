import { useRouter } from "next/router";

function Header() {
  const router = useRouter();

  const handleClickLogo = () => {
    router.push("/");
  };

  const handleClickCreate = () => {
    router.push("/create");
  };

  const handleClickVote = () => {
    router.push("/vote");
  };

  return (
    <div className="h-16 flex justify-between sticky top-0 items-center px-12 bg-white z-10">
      <button onClick={handleClickLogo}>LOGO</button>
      <div className="flex gap-8">
        <button
          className="hover:font-bold text-grayColor"
          onClick={handleClickVote}
        >
          투표 하기
        </button>
        <button
          className="hover:font-bold text-grayColor"
          onClick={handleClickCreate}
        >
          투표 만들기
        </button>
      </div>
    </div>
  );
}

export default Header;
