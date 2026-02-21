# DPO Fine-Tuning for Arithmetic Solver (NanoGPT-style)

This project fine-tunes a GPT-style language model using **Direct Preference Optimization (DPO)** to improve performance on arithmetic/algebra-style text problems (e.g. prompts like `72-x=34,x=?`).

The workflow uses:

- a **supervised fine-tuned (SFT) checkpoint** as the starting model (`sft/gpt.pt`)
- a **preference dataset** of positive/negative response pairs (`pos_neg_pairs.json`)
- a local GPT implementation from `model.py` (`GPT`, `GPTConfig`)

---

## What this project does

The notebook trains a model to prefer better answers over worse ones using DPO.

### Training pipeline (high level)

1. **Load tokenizer / vocab helpers**
   - `encode(...)`
   - `decode(...)`

2. **Load base GPT model**
   - Imports `GPT`, `GPTConfig` from `model.py`
   - Loads SFT checkpoint from `sft/gpt.pt`

3. **Load preference data**
   - Reads JSON from `pos_neg_pairs.json`
   - Each sample contains a **preferred** and **rejected** response (positive/negative pair)

4. **Run DPO training**
   - Uses `AdamW` optimizer
   - Saves checkpoint (e.g. `dpo.pt`)

5. **Run inference test**
   - Example prompt in notebook: `72-x=34,x=?`

---
