<script>
    import {
        BottomNav,
        BottomNavItem,
        Button,
        Input,
        Label,
        Modal,
        Navbar,
        NavBrand,
        NavHamburger,
        Tooltip
    } from 'flowbite-svelte'
    import {HomeSolid, PlusSolid, UserAddOutline} from 'flowbite-svelte-icons'
    import "../app.css"
    import {createRoom} from "$lib/apis/rooms.ts";
    import {goto} from "$app/navigation";

    let formModal = false;
    let title = "";

    const createRoomAction = async () => {
        const response = await createRoom(title);

        if (!response.ok) {
            const json = await response.json();
            alert = json.message;
        }

        const roomId = await response.json();
        await goto(`/room/${roomId}`);

        formModal = false;
        title = "";
    };
</script>

<Navbar let:hidden let:toggle>
    <NavBrand href="/">
        <span class="self-center whitespace-nowrap text-xl font-semibold dark:text-white">2048</span>
    </NavBrand>
    <NavHamburger on:click={toggle}/>
</Navbar>

<Modal bind:open={formModal} size="xs" autoclose={false} class="w-full">
    <form class="flex flex-col space-y-6" action="#">
        <h3 class="mb-4 text-xl font-medium text-gray-900 dark:text-white">방 만들기</h3>
        <Label class="space-y-2">
            <span>제목</span>
            <Input type="text" name="title" placeholder="재밌게 게임해보자!" required bind:value={title}/>
        </Label>
        <Button type="submit" class="w-full1" on:click={createRoomAction}>만들기</Button>
    </form>
</Modal>

<slot/>

<BottomNav position="absolute" navType="application" classInner="grid-cols-3">
    <BottomNavItem btnName="Home" appBtnPosition="left" href="/">
        <HomeSolid
                class="w-5 h-5 mb-1 text-gray-500 dark:text-gray-400 group-hover:text-primary-600 dark:group-hover:text-primary-500"/>
        <Tooltip arrow={false}>Home</Tooltip>
    </BottomNavItem>
    <div class="flex items-center justify-center">
        <BottomNavItem btnName="Create new item" appBtnPosition="middle" on:click={() => (formModal = true)}
                       btnClass="inline-flex items-center justify-center w-10 h-10 font-medium bg-primary-600 rounded-full hover:bg-primary-700 group focus:ring-4 focus:ring-primary-300 focus:outline-none dark:focus:ring-primary-800">
            <PlusSolid class="text-white"/>
            <Tooltip arrow={false}>Create new game</Tooltip>
        </BottomNavItem>
    </div>
    <!-- 로그인 했을 때의 아이콘 -->
    <!--    <BottomNavItem btnName="Profile" appBtnPosition="right">-->
    <!--        <UserCircleSolid-->
    <!--                class="w-5 h-5 mb-1 text-gray-500 dark:text-gray-400 group-hover:text-primary-600 dark:group-hover:text-primary-500"/>-->
    <!--        <Tooltip arrow={false}>Profile</Tooltip>-->
    <!--    </BottomNavItem>-->
    <BottomNavItem btnName="Profile" appBtnPosition="right" href="/api/oauth2/authorization/google">
        <UserAddOutline
                class="w-5 h-5 mb-1 text-gray-500 dark:text-gray-400 group-hover:text-primary-600 dark:group-hover:text-primary-500"/>
        <Tooltip arrow={false}>Profile</Tooltip>
    </BottomNavItem>
</BottomNav>