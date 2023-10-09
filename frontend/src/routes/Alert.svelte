<script lang="ts">
    export let alert: string;
</script>

{#if alert}
    <div class="error">
        {alert}
    </div>
{/if}

<style>
    .error {
        color: red;
    }
</style>
