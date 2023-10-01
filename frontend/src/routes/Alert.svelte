<script>
    export let alert;
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