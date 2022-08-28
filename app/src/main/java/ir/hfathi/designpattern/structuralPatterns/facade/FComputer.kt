package ir.hfathi.designpattern.structuralPatterns.facade

/* Facade */
class FComputer(val processor: CPU = CPU(), val ram: Memory = Memory(), val hd: HardDrive = HardDrive()) {
    companion object {
        val BOOT_ADDRESS = 0L
        val BOOT_SECTOR = 0L
        val SECTOR_SIZE = 0
    }

    fun start() {
        processor.freeze()
        ram.load(BOOT_ADDRESS, hd.read(BOOT_SECTOR, SECTOR_SIZE))
        processor.jump(BOOT_ADDRESS)
        processor.execute()
    }
}