<script>
    import {Button, Card} from 'flowbite-svelte';
</script>

<div class="flex justify-center">
    <Card class="text-center" size="lg" padding="xl">
        <h5 class="mb-2 text-3xl font-bold text-gray-900 dark:text-white">2048을 지금 한번 즐겨보세요!</h5>
        <p class="mb-5 text-base text-gray-500 sm:text-lg dark:text-gray-400">백엔드 개발자가 구성한 UI/UX는 크게 신뢰하지 마세요...</p>
        <div class="justify-center items-center space-y-4 sm:flex sm:space-y-0 sm:space-x-4">
            <Button href="/rooms">Play</Button>
        </div>
    </Card>
</div>