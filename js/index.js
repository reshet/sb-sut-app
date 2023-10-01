const express = require('express');
const bodyParser = require('body-parser');
const { Random } = require('random-js');

const app = express();
const port = 3200;
const random = new Random();

app.use(bodyParser.json());

app.get('/read-node', async (req, res) => {
    let waitTimeMs = 10 + random.integer(0, 50);
    await new Promise(resolve => setTimeout(resolve, waitTimeMs));
    res.json("Done");
});

app.listen(port, () => {
    console.log(`Server running on port ${port} ...`);
});