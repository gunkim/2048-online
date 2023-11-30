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
        Radio,
        Tooltip
    } from 'flowbite-svelte'
    import {HomeSolid, PlusSolid, UserAddOutline} from 'flowbite-svelte-icons'
    import "../app.css"
    import {createRoom, getTimer} from "$lib/apis/rooms.ts";
    import {goto} from "$app/navigation";

    let formModal = false;
    let title = "";
    let timer = 0;
    let timers = [];

    const createRoomAction = async () => {
        const response = await createRoom(title, timer);

        if (!response.ok) {
            const json = await response.json();
            alert = json.message;
        }

        const roomId = await response.json();
        await goto(`/room/${roomId}`);

        formModal = false;
        title = "";
    };

    const createRoomClick = async () => {
        const response = await getTimer();

        const value = await response.json();
        timers = value;
        formModal = true;
        timer = value[0].time;
    }
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
        <Label class="space-y-2">
            <span>시간</span>
            <ul class="items-center w-full rounded-lg border border-gray-200 sm:flex dark:bg-gray-800 dark:border-gray-600 divide-x divide-gray-200 dark:divide-gray-600">
                {#each timers as timeValue, index}
                    <li class="w-full">
                        <Radio name="hor-list" class="p-3" bind:group={timer} value={timeValue.time}
                               checked={timeValue===timers[0]}>{timeValue.time} 초
                        </Radio>
                    </li>
                {/each}
            </ul>
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
        <BottomNavItem btnName="Create new item" appBtnPosition="middle" on:click={createRoomClick}
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