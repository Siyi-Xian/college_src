#include <stdio.h>
#include <stdlib.h>

char *regnames[] = {"r0", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8", "r9", "r10", "r11", "r12", "sp", "lr", "pc"};
// char *opname[]   = {"ands", "eors", ... };

int pow2(int x) {
    int power = 1;
    while (x-- > 0) power *= 2;
    return power;
}

int reverse_bit(int x, int bit) {
    int i = 0, ret=0;
    while (i < bit) {
        ret += (x & 1) * pow2(bit - i++ - 1);
        x = x >> 1;
    }
    return ret;
}

int main() {
    printf (".text\n.syntax unified\n.thumb\n");
    
    int inst;
    while (scanf("%x", &inst) == 1){
        inst = reverse_bit(inst, 16);
        if (!(inst & 1)) { // 15 is 0
            inst >>= 1;
            if (!(inst & 1)) { // 14 is 0
                inst >>= 1;
                if (!(inst & 1)) { // 13 is 0
                    inst >>= 1;
                    if (!(inst & 0b11)) { // 12 and 11 is 0
                        inst >>= 2;
                        int imm5 = reverse_bit(inst & 0x1f, 5);
                        if (!imm5) printf("\tmovs\t");
                        else printf("\tlsls\t");
                        inst >>= 5;
                        int rm = reverse_bit(inst & 0b111, 3);
                        inst >>= 3;
                        int rn = reverse_bit(inst & 0b111, 3);
                        printf("%s, %s", regnames[rn], regnames[rm]);
                        if (imm5) printf(", #%d\n", imm5);
                        else printf("\n");
                    }
                    else if ((inst & 0b11) == 0b11) { // 12 and 11 is 11
                        inst >>= 2;
                        int rig_imm = inst & 0b1;
                        inst >>= 1;
                        char *opname[] = {"adds", "subs"};
                        int opc = inst & 0b1;
                        inst >>= 1;
                        int rm = reverse_bit(inst & 0b111, 3);
                        inst >>= 3;
                        int rn = reverse_bit(inst & 0b111, 3);
                        inst >>= 3;
                        int rd = reverse_bit(inst & 0b111, 3);
                        printf("\t%s\t%s, %s, ", opname[opc], regnames[rd], regnames[rn]);
                        if (!rig_imm) // 10 is 0
                            printf("%s\n", regnames[rm]);
                        else // 10 is 1
                            printf("#%d\n", rm);
                    }
                    else { // 12 and 11 is 01 or 10
                        char *opname[] = {"", "lsrs", "asrs", ""};
                        int opc = reverse_bit(inst & 0b11, 2);
                        inst >>= 2;
                        int imm5 = reverse_bit(inst & 0x1f, 5);
                        inst >>= 5;
                        int rm = reverse_bit(inst & 0b111, 3);
                        inst >>= 3;
                        int rn = reverse_bit(inst & 0b111, 3);
                        printf("\t%s\t%s, %s, #%d\n", opname[opc], regnames[rn], regnames[rm], imm5);
                    }
                }
                else { // 13 is 1
                    inst >>= 1;
                    char *opname[] = {"movs", "cmp ", "adds", "subs"};
                    int opc = reverse_bit(inst & 0b11, 2);
                    inst >>= 2;
                    int rd = reverse_bit(inst & 0b111, 3);
                    inst >>= 3;
                    int imm8 = reverse_bit(inst & 0xff, 8);
                    printf("\t%s\t%s, #%d\n", opname[opc], regnames[rd], imm8);
                }
            }
            else { // 14 is 1
                inst >>= 1;
                if (!(inst & 0xf)) { // 13 12 11 10 is 0000
                    inst >>= 4;
                    char *opname[] = {"ands", "eors", "lsls", "lsrs", "asrs", "adcs", "sbcs", "rors", "tst ", "rsbs", "cmp ", "cmn ", "orrs", "muls", "bics", "mvns"};
                    int opc = reverse_bit(inst & 0xf, 4);
                    inst >>= 4;
                    int rm = reverse_bit(inst & 0b111, 3);
                    inst >>= 3;
                    int rd = reverse_bit(inst & 0b111, 3);
                    printf("\t%s\t%s, %s", opname[opc], regnames[rd], regnames[rm]);
                    switch (opc) {
                        case 9:
                            printf(", #0");
                            break;
                        case 13:
                            printf(", %s", regnames[rd]);
                            break;
                    }
                    printf("\n");
                }
                else if ((inst & 0xf) == 0b1000) { // 13 12 11 10 is 0001
                    inst >>= 4;
                    char *opname[] = {"add", "cmp", "mov"};
                    int opc = reverse_bit(inst & 0b11, 2);
                    inst >>= 2;
                    int dn = inst & 0b1;
                    inst >>= 1;
                    int rm = reverse_bit(inst & 0xf, 4);
                    inst >>= 4;
                    int rn = reverse_bit(inst & 0b111, 3);
                    rn += dn * pow2(3);
                    printf("\t%s \t%s, %s\n", opname[opc], regnames[rn], regnames[rm]);
                }
                else if (inst & 1) { // 13 is 1
                    inst >>= 1;
                    char *opname[] = {"str ", "ldr ", "strb", "ldrb"};
                    int opc = reverse_bit(inst & 0b11, 2);
                    inst >>= 2;
                    int imm5 = reverse_bit(inst & 0x1f, 5);
                    inst >>= 5;
                    int rm = reverse_bit(inst & 0b111, 3);
                    inst >>= 3;
                    int rn = reverse_bit(inst & 0b111, 3);
                    printf("\t%s\t%s, [%s, #", opname[opc], regnames[rn], regnames[rm]);
                    if (opc < 2)
                        printf("%d]\n", imm5 * 4);
                    else
                        printf("%d]\n", imm5);
                }
            }
        }
        else if ((inst & 0b111) == 1) { // 15 14 13 is 100
            inst >>= 3;
            if (!(inst & 1)) { // 12 is 0
                inst >>= 1;
                char *opname[] = {"strh", "ldrh"};
                int opc = inst & 1;
                inst >>= 1;
                int imm5 = reverse_bit(inst & 0x1f, 5);
                inst >>= 5;
                int rm = reverse_bit(inst & 0b111, 3);
                inst >>= 3;
                int rn = reverse_bit(inst & 0b111, 3);
                printf("\t%s\t%s, [%s, #%d]\n", opname[opc], regnames[rn], regnames[rm], imm5 * 2);
            }
            else { // 12 is 1
                inst >>= 1;
                char *opname[] = {"str", "ldr"};
                int opc = inst & 1;
                inst >>= 1;
                int rt = reverse_bit(inst & 0b111, 3);
                inst >>= 3;
                int imm8 = reverse_bit(inst & 0xff, 8);
                printf("\t%s \t%s, [sp, #%d]\n", opname[opc], regnames[rt], imm8 * 4);
            }
        }
    }
}
